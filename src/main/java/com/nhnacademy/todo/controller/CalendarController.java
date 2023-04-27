package com.nhnacademy.todo.controller;

import com.nhnacademy.todo.dto.DailyRegisterCountRequestDto;
import com.nhnacademy.todo.dto.DailyRegisterCountResponseDto;
import com.nhnacademy.todo.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 16/03/2023
 */
@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final EventService eventService;

    @GetMapping("/daily-register-count")
    public DailyRegisterCountResponseDto dailyRegisterCount(@Valid DailyRegisterCountRequestDto dailyRegisterCountRequestDto){
        return eventService.getDayliyRegisterCount(dailyRegisterCountRequestDto.getDate());
    }

}
