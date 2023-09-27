package com.nhnacademy.todo.mapper;

import com.nhnacademy.todo.domain.Event;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EventMapper {
    void save(Event event);

    Event getEventById(@Param("id") long eventId);

}

