package com.nhnacademy.todo.repository;

import com.nhnacademy.todo.domain.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository {
    //저장
    Event save(Event event);

    //수정
    void update(Event event);

    //삭제
    void deleteById(String userId, long eventId);

    //조회
    Event getEvent(String userId, long eventId);
    Event getEvent(long eventId);

    //일단위 조회
    List<Event> findAllByDay(String userId, int year, int month, int day);

    //월단위 조회
    List<Event> findAllByMonth(String userId, int year, int month);

    //일별 등록 카운트
    long countByUserIdAndEventAt(String userId, LocalDate targetDate);

    //일별 삭제
    void deletebyDaily(String userId, LocalDate targetDate);

    void init();
}
