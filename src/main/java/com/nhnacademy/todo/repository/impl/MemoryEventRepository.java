package com.nhnacademy.todo.repository.impl;

import com.nhnacademy.todo.domain.Event;
import com.nhnacademy.todo.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Slf4j
public class MemoryEventRepository implements EventRepository {

    private final ConcurrentMap<String, List<Event>> eventMap = new ConcurrentHashMap<>();
    private final AtomicLong autoIdx = new AtomicLong();

    public void init(){
        List<Event> eventList =Collections.synchronizedList(new ArrayList<>());
        for(int i=1; i<=8; i++){
            Event event = new Event("marco","할일-" + i, LocalDate.now());
            event.setId(autoIdx.addAndGet(1l));
            eventList.add(event);
        }
        eventMap.put("marco", eventList);
    }

    @Override
    public Event save(Event event) {

        event.setId(autoIdx.addAndGet(1l));
        List<Event> eventList = null;
        if(eventMap.containsKey(event.getUserId())){
            eventList = eventMap.get(event.getUserId());
            eventList.add(event);
        }else{
            eventList = Collections.synchronizedList(new ArrayList<>());
            eventList.add(event);
            eventMap.put(event.getUserId(),eventList);
        }

        return event;
    }

    @Override
    public void update(Event event) {
        if(eventMap.containsKey(event.getUserId())){
            List<Event> eventList = eventMap.get(event.getUserId());
            Event target = eventList.stream()
                    .filter(o->o.getId()==event.getId())
                    .findFirst()
                    .orElse(null);
            if(Objects.nonNull(target)){
                target.update(event.getSubject());
            }
        }
    }

    @Override
    public void deleteById(String userId, long eventId) {
        if(eventMap.containsKey(userId)){
            eventMap.get(userId).removeIf(o->o.getId() == eventId);
        }
    }

    @Override
    public Event getEvent(String userId, long eventId) {
        if(eventMap.containsKey(userId)){
            return eventMap.get(userId).stream()
                    .filter(o->o.getId() == eventId)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
    public Event getEvent(long eventId) {
        return eventMap.values().stream()
                    .flatMap(Collection::stream)
                    .filter(o->o.getId()==eventId).findFirst()
                    .orElse(null);
    }

    @Override
    public List<Event> findAllByDay(String userId, int year, int month, int day) {
        LocalDate targetDate = LocalDate.of(year,month,day);
        if(eventMap.containsKey(userId)){
            return eventMap.get(userId).stream()
                    .filter(o->o.getEventAt().equals(targetDate))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Event> findAllByMonth(String userId, int year, int month) {
        if(eventMap.containsKey(userId)){
            return eventMap.get(userId).stream()
                    .filter(o->o.getEventAt().getYear() == year)
                    .filter(o->o.getEventAt().getMonthValue() == month)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public long countByUserIdAndEventAt(String userId, LocalDate targetDate) {
        if(eventMap.containsKey(userId)){
            return eventMap.get(userId).stream()
                    .filter(o->o.getEventAt().equals(targetDate))
                    .count();
        }
        return 0;
    }

    @Override
    public void deletebyDaily(String userId, LocalDate targetDate) {
        if(eventMap.containsKey(userId)){
            List<Event> eventList = eventMap.get(userId);
            eventList.removeIf(o->o.getEventAt().equals(targetDate));
        }
    }
}
