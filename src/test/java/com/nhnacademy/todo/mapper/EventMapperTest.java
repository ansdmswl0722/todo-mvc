package com.nhnacademy.todo.mapper;

import com.nhnacademy.todo.config.RootConfig;
import com.nhnacademy.todo.domain.Event;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RootConfig.class)
@Transactional // 롤백이 된다.
class EventMapperTest {

    @Autowired
    EventMapper eventMapper;

    @Test
    @DisplayName("save event")
    void save() {
        Event event = new Event("moon","mybatis", LocalDate.now());
        eventMapper.save(event);
        Assertions.assertThat(event.getId()).isNotZero();
    }
}