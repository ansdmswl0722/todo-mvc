package com.nhnacademy.todo.controller;

import com.nhnacademy.todo.dto.EventCreatedResponseDto;
import com.nhnacademy.todo.dto.EventDto;
import com.nhnacademy.todo.exception.ValidationFailedException;
import com.nhnacademy.todo.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/calendar/events")
public class EventController {

    private final EventService eventService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = {"/" ,""})
    public EventCreatedResponseDto createEvent(@RequestBody @Valid EventDto eventDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        return eventService.insert(eventDto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/{event-id}")
    public long updateEvent(@PathVariable("event-id") long eventId, @Valid @RequestBody EventDto eventDto, BindingResult bindingResult ){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        return eventService.update(eventId,eventDto);
    }

    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @DeleteMapping("/{event-id}")
    public void deleteEvent(@PathVariable("event-id") long eventId ){
        eventService.deleteOne(eventId);
    }

    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @DeleteMapping("/daily/{eventAt}")
    public void deleteEventByDaily(@PathVariable("eventAt")String eventAt){
        eventService.deleteEventByDaily(LocalDate.parse(eventAt, DateTimeFormatter.ISO_DATE));
    }

    //envet 조회
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{event-id}")
    public EventDto getEvent(@PathVariable("event-id") long eventId ){
        return eventService.getEvent(eventId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = {"/",""})
    public List<EventDto> getEventList(@RequestParam(name = "year", required = true) Integer year,
                                       @RequestParam(name = "month", required = true) Integer month,
                                       @RequestParam(name="day", required = false) Integer day
    ){

        List<EventDto> eventDtos = null;
        if(Objects.isNull(day)){
            eventDtos = eventService.getEventListByMonthly(year,month);
        }else{
            eventDtos = eventService.getEventListBydaily(year, month, day);
        }
        return eventDtos;

    }



}
