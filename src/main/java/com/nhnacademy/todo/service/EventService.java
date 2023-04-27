package com.nhnacademy.todo.service;

import com.nhnacademy.todo.dto.DailyRegisterCountResponseDto;
import com.nhnacademy.todo.dto.EventCreatedResponseDto;
import com.nhnacademy.todo.dto.EventDto;
import com.nhnacademy.todo.exception.InvalidEventOwnerException;
import com.nhnacademy.todo.share.UserIdStore;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    //등록
    EventCreatedResponseDto insert(EventDto eventDto);

    //수정
    long update(long eventId, EventDto eventDto);

    //삭제
    void deleteOne(long eventId);

    //조회
    EventDto getEvent(long eventId);

    //월단위 조회
    List<EventDto> getEventListByMonthly(Integer year, Integer month);

    //일단위 조회
    List<EventDto> getEventListBydaily(Integer year, Integer month, Integer day);

    //일 단위 등록 카윤트
    DailyRegisterCountResponseDto getDayliyRegisterCount(LocalDate targetDate);

    //삭제
    void deleteEventByDaily(LocalDate eventAt);

    //event 소유자 체크
    default boolean checkOwner(String dbUserId){
        if(!UserIdStore.getUserId().equals(dbUserId)){
            throw new InvalidEventOwnerException();
        }
        return true;
    }
}
