package com.nhnacademy.todo.mapper;

import com.nhnacademy.todo.domain.Event;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EventMapper {
    void save(Event event);

    Event getEventById( long id);

    List<Event> findAllByMonth(String day);

    List<Event> findAllByDaily(LocalDate day);

    @Update("update event set subject = #{subject} where id=#{id}")
    void update(Event event);

    @Delete("Delete from event where id = #{id}")
    void deleteOne(long id);

    @Delete("Delete from event where event_at = #{day}")
    void deleteByDaily(LocalDate day);

    @Select("select  count(*) from event where event_at = #{day}")
    int countByEventAt(LocalDate day);

}

