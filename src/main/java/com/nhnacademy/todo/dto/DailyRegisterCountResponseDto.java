package com.nhnacademy.todo.dto;

import lombok.Getter;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 16/03/2023
 */

@Getter
public class DailyRegisterCountResponseDto {
    private final long count;

    public DailyRegisterCountResponseDto(long count) {
        this.count = count;
    }
}
