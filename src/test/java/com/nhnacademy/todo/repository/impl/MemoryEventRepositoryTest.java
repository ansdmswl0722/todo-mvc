package com.nhnacademy.todo.repository.impl;

import com.nhnacademy.todo.domain.Event;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemoryEventRepositoryTest {

    private static MemoryEventRepository memoryEventRepository = new MemoryEventRepository();

    @BeforeAll
    static void setUp() {
        for(int i=1; i<=8; i++){
            memoryEventRepository.save(new Event("marco","할일-" + i, LocalDate.now()));
        }
    }

    @Order(1)
    @Test
    @DisplayName("event 저장")
    void save() {
        //given
        Event dummyEvent = new Event("marco", "spring study1", LocalDate.now());
        //when

        Event event = memoryEventRepository.save(dummyEvent);
        //then

        //then
        Assertions.assertThat(event.getId()).isNotZero();

    }
    @Order(2)
    @Test
    @DisplayName("event 수정")
    void update() {
        //given
        Event dummyEvent = new Event("marco", "spring study2", LocalDate.now());
        ReflectionTestUtils.setField(dummyEvent,"id",1l);
        //when
        memoryEventRepository.update(dummyEvent);

        //then
        Event event = memoryEventRepository.getEvent(1l);
        log.info("event:{}",event);
        Assertions.assertThat(event.getSubject()).isEqualTo(dummyEvent.getSubject());
        Assertions.assertThat(event.getEventAt()).isEqualTo(dummyEvent.getEventAt());

    }

    @Order(3)
    @Test
    @DisplayName("envent 삭제")
    void deleteById() {
        //when
        memoryEventRepository.deleteById("marco",1l);

        //then
        Event event = memoryEventRepository.getEvent("marco", 1l);
        Assertions.assertThat(event).isNull();

    }
    @Order(4)
    @ParameterizedTest
    @ValueSource(longs = {2,3,4,5})
    @DisplayName("event 조회")
    void getEvent(long eventId) {
        Event event = memoryEventRepository.getEvent(eventId);
        Assertions.assertThat(event).isNotNull();
    }

    @Order(5)
    @Test
    @DisplayName("일별 조회")
    void findAllByDay() {

        //when
        List<Event> eventList = memoryEventRepository.findAllByDay("marco",LocalDate.now().getYear(), LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth());

        //then
        Assertions.assertThat(eventList).hasSize(8);

    }

    @Order(6)
    @Test
    @DisplayName("월별 조회")
    void findAllByMonth() {

        //when
        List<Event> eventList = memoryEventRepository.findAllByMonth("marco",LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        //then
        Assertions.assertThat(eventList).hasSize(8);
    }

    @Order(7)
    @Test
    @DisplayName("해당 일자의 등록된 event count")
    void countByUserIdAndEventAt() {
        //when
        long count = memoryEventRepository.countByUserIdAndEventAt("marco", LocalDate.now());

        //then
        Assertions.assertThat(count).isEqualTo(8);
    }

    @Order(8)
    @Test
    @DisplayName("해당 일자의 모든 Event 삭제")
    void deletebyDaily() {
        //when
        memoryEventRepository.deletebyDaily("marco",LocalDate.now());
        //then
        long count = memoryEventRepository.countByUserIdAndEventAt("marco",LocalDate.now());
        Assertions.assertThat(count).isZero();
    }
}