package com.nhnacademy.todo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 16/03/2023
 */
@Getter
@Setter
public class DailyRegisterCountRequestDto {
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

}
