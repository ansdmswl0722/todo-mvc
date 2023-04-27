package com.nhnacademy.todo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 15/03/2023
 */

@Getter
@NoArgsConstructor
@ToString
public class EventDto {
    private long id;

    @NotBlank(message = "제목을 필수값 입니다.")
    private String subject;

    @NotBlank(message = "날짜는 필수값 입니다.")
    private LocalDate eventAt;

    private LocalDateTime createdAt;

    @Builder
    public EventDto(long id, String subject, LocalDate eventAt) {
        this.id = id;
        this.subject = subject;
        this.eventAt = eventAt;
        this.createdAt = LocalDateTime.now();
    }

}
