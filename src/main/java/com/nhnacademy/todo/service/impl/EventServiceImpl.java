package com.nhnacademy.todo.service.impl;

import com.nhnacademy.todo.domain.Event;
import com.nhnacademy.todo.dto.DailyRegisterCountResponseDto;
import com.nhnacademy.todo.dto.EventCreatedResponseDto;
import com.nhnacademy.todo.dto.EventDto;
import com.nhnacademy.todo.repository.EventRepository;
import com.nhnacademy.todo.service.EventService;
import com.nhnacademy.todo.share.UserIdStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public EventCreatedResponseDto insert(EventDto eventDto){
        Event target = new Event(UserIdStore.getUserId(),eventDto.getSubject(),eventDto.getEventAt());
        Event event = eventRepository.save(target);
        return new EventCreatedResponseDto(event.getId());
    }

    @Override
    public long update(long eventId, EventDto eventDto) {
        Event target = new Event(UserIdStore.getUserId(), eventDto.getSubject(),eventDto.getEventAt());
        target.setId(eventId);
        eventRepository.update(target);
        return eventId;
    }

    @Override
    public void deleteOne(long eventId) {
        eventRepository.deleteById(UserIdStore.getUserId(),eventId);
    }

    @Override
    public EventDto getEvent(long eventId) {
        Event event =  eventRepository.getEvent(eventId);

        if(Objects.isNull(event)){
            return null;
        }

        checkOwner(event.getUserId());

        return new EventDto(event.getId(),event.getSubject(),event.getEventAt());
    }

    @Override
    public List<EventDto> getEventListByMonthly(Integer year, Integer month) {
        List<Event> eventList =  eventRepository.findAllByMonth(UserIdStore.getUserId(),year,month);
        List<EventDto> eventDtos = new ArrayList<>();
        for(Event event : eventList){
            eventDtos.add(new EventDto(event.getId(),event.getSubject(),event.getEventAt()));
        }
        return eventDtos;
    }

    @Override
    public List<EventDto> getEventListBydaily(Integer year, Integer month, Integer day) {
        List<Event> eventList = eventRepository.findAllByDay(UserIdStore.getUserId(),year,month,day);
        List<EventDto> eventDtos = new ArrayList<>();
        for(Event event : eventList){
            eventDtos.add(new EventDto(event.getId(),event.getSubject(),event.getEventAt()));
        }
        return eventDtos;
    }

    @Override
    public DailyRegisterCountResponseDto getDayliyRegisterCount(LocalDate targetDate) {
        long count =  eventRepository.countByUserIdAndEventAt(UserIdStore.getUserId(), targetDate);
        return new DailyRegisterCountResponseDto(count);
    }

    @Override
    public void deleteEventByDaily(LocalDate eventAt) {
        eventRepository.deletebyDaily(UserIdStore.getUserId(),eventAt);
    }
}
