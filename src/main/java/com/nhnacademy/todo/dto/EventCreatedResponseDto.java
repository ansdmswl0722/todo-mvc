package com.nhnacademy.todo.dto;

import lombok.Getter;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 15/03/2023
 */

@Getter
public class EventCreatedResponseDto {
    private final long id;

    public EventCreatedResponseDto(long id) {
        this.id = id;
    }
}
