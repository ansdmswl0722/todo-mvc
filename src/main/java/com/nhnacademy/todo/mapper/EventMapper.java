package com.nhnacademy.todo.mapper;

import com.nhnacademy.todo.domain.Event;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EventMapper {
    void save(Event event);

    Event getEventById( long id);

    List<Event> findAllByMonth(String day);

    List<Event> findAllByDaily(LocalDate day);
}

