package com.nhnacademy.todo.service.impl;

import com.nhnacademy.todo.domain.Event;
import com.nhnacademy.todo.dto.DailyRegisterCountResponseDto;
import com.nhnacademy.todo.dto.EventCreatedResponseDto;
import com.nhnacademy.todo.dto.EventDto;
import com.nhnacademy.todo.exception.InvalidEventOwnerException;
import com.nhnacademy.todo.repository.EventRepository;
import com.nhnacademy.todo.service.EventService;
import com.nhnacademy.todo.share.UserIdStore;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.springframework.test.util.ReflectionTestUtils;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventServiceImplTest {

    private static EventService eventService;
    private static EventRepository eventRepository;
    private static MockedStatic<UserIdStore> userIdStore;


    @BeforeAll
    static void  setUp(){

        eventRepository = mock(EventRepository.class);
        userIdStore = mockStatic(UserIdStore.class);
        eventService = new EventServiceImpl(eventRepository);
    }


    @Test
    @DisplayName("event-등록")
    void insert() {

        //given (준비)
        EventDto eventDto = EventDto.builder()
                .subject("java study")
                .eventAt(LocalDate.now())
                .build();
        Event event = new Event("marco","java study", LocalDate.now());
        ReflectionTestUtils.setField(event,"id",100l);
        userIdStore.when(UserIdStore::getUserId).thenReturn("marco");
        when(eventRepository.save(any())).thenReturn(event);

        //when(실행)
        EventCreatedResponseDto eventCreatedResponseDto = eventService.insert(eventDto);

        //then(검증)
        Assertions.assertThat(eventCreatedResponseDto.getId()).isEqualTo(100l);

    }

    @Test
    @DisplayName("event 수정")
    void update() {
        //given
        EventDto eventDto = EventDto.builder()
                .subject("spring study")
                .eventAt(LocalDate.now())
                .build();
        Event event = new Event("marco","spring study", LocalDate.now());
        ReflectionTestUtils.setField(event,"id",100l);
        userIdStore.when(UserIdStore::getUserId).thenReturn("marco");
        when(eventRepository.save(any())).thenReturn(event);

        //when
        long eventId = eventService.update(100l, eventDto);

        //then
        Assertions.assertThat(eventId).isEqualTo(100l);

    }

    @Test
    @DisplayName("event 조회")
    void getEvent() {
        //given
        Event event = new Event("marco","spring study", LocalDate.now());
        ReflectionTestUtils.setField(event,"id",100l);
        when(eventRepository.getEvent(100l)).thenReturn(event);
        userIdStore.when(UserIdStore::getUserId).thenReturn("marco");

        //when
        EventDto eventDto = eventService.getEvent(100l);

        //then
        Assertions.assertThat(eventDto.getId()).isEqualTo(100l);
        Assertions.assertThat(eventDto.getSubject()).isEqualTo("spring study");
        Assertions.assertThat(eventDto.getEventAt()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("event is null")
    void getEvent_notFound(){
        //given
        when(eventRepository.getEvent(anyLong())).thenReturn(null);
        userIdStore.when(UserIdStore::getUserId).thenReturn("marco");

        //when
        EventDto eventDto = eventService.getEvent(100l);
        Assertions.assertThat(eventDto).isNull();
    }

    @Test
    @DisplayName("event 소유자가 다를 때")
    void getEvent_invalidOwner(){
        //given
        Event event = new Event("marco","spring study", LocalDate.now());
        when(eventRepository.getEvent(anyLong())).thenReturn(event);
        userIdStore.when(UserIdStore::getUserId).thenReturn("abc");

        //then
        Assertions.assertThatExceptionOfType(InvalidEventOwnerException.class).isThrownBy(()->{
            //when
            eventService.getEvent(100l);
        });

    }


    @Test
    @DisplayName("event 삭제")
    void deleteOne() {
        //given
        userIdStore.when(UserIdStore::getUserId).thenReturn("marco");
        doNothing().when(eventRepository).deleteById(anyString(), anyLong());

       //when
        eventService.deleteOne(100l);

        //then
        verify(eventRepository, atMostOnce()).deleteById(anyString(),anyLong());

    }



    @Test
    @DisplayName("월단위 조회")
    void getEventListByMonth() {
        //given
        List<Event> eventList = List.of(
                new Event("marco","spring study1",LocalDate.now()),
                new Event("marco","spring study2",LocalDate.now()),
                new Event("marco","spring study3",LocalDate.now())
        );

        userIdStore.when(UserIdStore::getUserId).thenReturn("marco");
        when(eventRepository.findAllByMonth(anyString(),anyInt(),anyInt())).thenReturn(eventList);

        //when
        List<EventDto> eventDtos = eventService.getEventListByMonthly(LocalDate.now().getYear(),LocalDate.now().getMonthValue());

        //then
        Assertions.assertThat(eventDtos).hasSize(3);
        Assertions.assertThat(eventDtos.get(0).getSubject()).isEqualTo("spring study1");
        Assertions.assertThat(eventDtos.get(1).getSubject()).isEqualTo("spring study2");
        Assertions.assertThat(eventDtos.get(2).getSubject()).isEqualTo("spring study3");

    }

    @Test
    @DisplayName("일단위 조회")
    void getEventListByday() {

        //given
        List<Event> eventList = List.of(
                new Event("marco","spring study1",LocalDate.now()),
                new Event("marco","spring study2",LocalDate.now()),
                new Event("marco","spring study3",LocalDate.now())
        );

        userIdStore.when(UserIdStore::getUserId).thenReturn("marco");
        when(eventRepository.findAllByDay(anyString(),anyInt(),anyInt(),anyInt())).thenReturn(eventList);

        //when
        List<EventDto> eventDtos = eventService.getEventListBydaily(LocalDate.now().getYear(),LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());


        //then
        Assertions.assertThat(eventDtos).hasSize(3);
        Assertions.assertThat(eventDtos.get(0).getSubject()).isEqualTo("spring study1");
        Assertions.assertThat(eventDtos.get(1).getSubject()).isEqualTo("spring study2");
        Assertions.assertThat(eventDtos.get(2).getSubject()).isEqualTo("spring study3");

    }

    @Test
    @DisplayName("일-등록 카운트")
    void getDayliyRegisterCount() {
        //given
        userIdStore.when(UserIdStore::getUserId).thenReturn("marco");
        when(eventRepository.countByUserIdAndEventAt(anyString(),isA(LocalDate.class))).thenReturn(3l);

        //when
        DailyRegisterCountResponseDto dailyRegisterCountResponseDto = eventService.getDayliyRegisterCount(LocalDate.now());

        //then
        Assertions.assertThat(dailyRegisterCountResponseDto.getCount()).isEqualTo(3l);
    }

    @Test
    @DisplayName("일 단위 삭제")
    void deleteEventByDaily() {
        //given
        userIdStore.when(UserIdStore::getUserId).thenReturn("marco");
        doNothing().when(eventRepository).deletebyDaily(anyString(),isA(LocalDate.class));

        //when
        eventService.deleteEventByDaily(LocalDate.now());

        //then
        verify(eventRepository,atMostOnce()).deletebyDaily(anyString(),isA(LocalDate.class));

    }
}