package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public void createEvent(Long userId, String title, LocalDate date, String location, LocalTime time) {
        Event event = new Event();
        event.setUserId(userId);
        event.setTitle(title);
        event.setDate(date);
        event.setLocation(location);
        event.setTime(time);

        eventRepository.save(event);
    }

    public List<Event> getEventsByUserId(Long userId) {
        return eventRepository.findByUserId(userId);
    }
}