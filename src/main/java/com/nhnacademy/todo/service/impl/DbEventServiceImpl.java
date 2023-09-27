package com.nhnacademy.todo.service.impl;

import com.nhnacademy.todo.domain.Event;
import com.nhnacademy.todo.dto.DailyRegisterCountResponseDto;
import com.nhnacademy.todo.dto.EventCreatedResponseDto;
import com.nhnacademy.todo.dto.EventDto;
import com.nhnacademy.todo.mapper.EventMapper;
import com.nhnacademy.todo.service.EventService;
import com.nhnacademy.todo.share.UserIdStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

        return 0;
    }

    @Override
    public void deleteOne(long eventId) {

    }

    @Override
    public EventDto getEvent(long eventId) {
        Event event = eventMapper.getEventById(eventId);
        if(Objects.isNull(event)){
            return null;
        }
        checkOwner(event.getUserId());
        return new EventDto(event.getId(),event.getSubject(),event.getEventAt());
    }

    @Override
    public List<EventDto> getEventListByMonthly(Integer year, Integer month) {
        return null;
    }

    @Override
    public List<EventDto> getEventListBydaily(Integer year, Integer month, Integer day) {
        return null;
    }

    @Override
    public DailyRegisterCountResponseDto getDayliyRegisterCount(LocalDate targetDate) {
        return null;
    }

    @Override
    public void deleteEventByDaily(LocalDate eventAt) {

    }
}
