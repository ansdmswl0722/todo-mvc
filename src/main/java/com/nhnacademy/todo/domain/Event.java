package com.nhnacademy.todo.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Data
public class Event {
    //아이디
    private long id;

    //userId
    @JsonProperty("user_id")
    private String userId;

    //제목
    private String subject;

    //event 일자
    @JsonProperty("event_at")
    private LocalDate eventAt;

    //event 등록 datetime
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public Event(String userId, String subject, LocalDate eventAt) {
        this.userId = userId;
        this.subject = subject;
        this.eventAt = eventAt;
        this.createdAt = LocalDateTime.now();
    }

    public Event(long id, String userId, String subject, LocalDate eventAt) {
        this.id = id;
        this.userId = userId;
        this.subject = subject;
        this.eventAt = eventAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void update(String subject){
        this.subject = subject;
    }
}
