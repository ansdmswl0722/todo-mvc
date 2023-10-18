package com.nhnacademy.todo.service.impl;

import com.nhnacademy.todo.domain.Event;
import com.nhnacademy.todo.dto.DailyRegisterCountResponseDto;
import com.nhnacademy.todo.dto.EventCreatedResponseDto;
import com.nhnacademy.todo.dto.EventDto;
import com.nhnacademy.todo.exception.EventNotFoundException;
import com.nhnacademy.todo.mapper.EventMapper;
import com.nhnacademy.todo.service.EventService;
import com.nhnacademy.todo.share.UserIdStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
@Transactional
public class DbEventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    @Override
    public EventCreatedResponseDto insert(EventDto eventDto) {
        Event event = new Event(UserIdStore.getUserId(), eventDto.getSubject(), eventDto.getEventAt());
        eventMapper.save(event);
        return new EventCreatedResponseDto(event.getId());
    }

    @Override
    public long update(long eventId, EventDto eventDto) {
        Event event = eventMapper.getEventById(eventId);
        if(Objects.isNull(event)){
            throw new EventNotFoundException(eventId);
        }
        Event target = new Event(eventId, eventDto.getSubject());
        eventMapper.update(target);
        return eventId;
    }

    @Override
    public void deleteOne(long eventId) {
        Event event = eventMapper.getEventById(eventId);
        if(Objects.isNull(event)){
            throw new EventNotFoundException(eventId);
        }
        eventMapper.deleteOne(eventId);
    }

    @Override
    public EventDto getEvent(long eventId) {
        Event event = eventMapper.getEventById(eventId);
        if(Objects.isNull(event)){
            throw new EventNotFoundException(eventId);
        }
        checkOwner(event.getUserId());
        return new EventDto(eventId,event.getSubject(),event.getEventAt());
    }

    @Override
    public List<EventDto> getEventListByMonthly(Integer year, Integer month) {
        String day = year + "-" + convertMonth(month);
        List<Event> eventList =  eventMapper.findAllByMonth(day);
        List<EventDto> eventDtos = new ArrayList<>();
        for(Event event : eventList){
            eventDtos.add(new EventDto(event.getId(),event.getSubject(),event.getEventAt()));
        }
        return eventDtos;

    }

    @Override
    public List<EventDto> getEventListBydaily(Integer year, Integer month, Integer day) {
        LocalDate targetDate = LocalDate.of(year,month,day);
        List<Event> eventList = eventMapper.findAllByDaily(targetDate);
        List<EventDto> eventDtos = new ArrayList<>();
        for(Event event : eventList){
            eventDtos.add(new EventDto(event.getId(),event.getSubject(),event.getEventAt()));
        }
        return eventDtos;
    }

    @Override
    public DailyRegisterCountResponseDto getDayliyRegisterCount(LocalDate targetDate) {
        return null;
    }

    @Override
    public void deleteEventByDaily(LocalDate eventAt) {
        eventMapper.deleteByDaily(eventAt);

    }
    public String convertMonth(int month){
        if(month<10){
            return "0"+month;
        }else{
            return month+"";
        }
    }
}
