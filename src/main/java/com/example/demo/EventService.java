package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
        event.setImagePath("./images/beach.jpg");

        eventRepository.save(event);
    }

    public List<Event> getEventsByUserId(Long userId) {
        return eventRepository.findByUserId(userId);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }

    public void joinEvent(Long eventId, Long userId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isEmpty()) {
            throw new IllegalArgumentException("Event not found");
        }
        Event event = optionalEvent.get();
        event.addParticipant(userId);
        eventRepository.save(event);
    }

    public int getParticipantCount(Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isEmpty()) {
            throw new IllegalArgumentException("Event not found");
        }
        return optionalEvent.get().getParticipants().size();
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void save(Event event) {
        eventRepository.save(event);
    }
}