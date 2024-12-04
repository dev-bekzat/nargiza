package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.Map;


@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping("/create")
    public String showCreateEventPage() {
        return "create";
    }

    @PostMapping("/create")
    public String createEvent(@RequestParam String title,
                              @RequestParam String date,
                              @RequestParam String location,
                              @RequestParam String time,
                              Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        eventService.createEvent(user.getId(), title, LocalDate.parse(date), location, LocalTime.parse(time));

        return "redirect:/events";
    }

    @PostMapping("/event/join")
    public String joinEvent(@RequestParam Long eventId, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        Event event = eventService.getEventById(eventId);
        event.addParticipant(user.getId()); // Добавление участника

        // Убедитесь, что здесь сохраняются корректные данные
        eventService.save(event);

        return "redirect:/events";
    }

    @GetMapping("/events")
    public String showEventsPage(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/sign-in"; // Перенаправить на страницу входа
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        List<Event> userEvents = eventService.getEventsByUserId(user.getId()); // События пользователя
        List<Event> allEvents = eventService.getAllEvents(); // Все события

        // LocalDateTime dateTime = LocalDateTime.parse("2024-11-30T16:56:07");

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        allEvents.forEach(event -> {
            event.getParticipants().replaceAll((k, v) -> {
                if (v instanceof LocalDateTime) {
                    return v;
                }
                try {
                    return LocalDateTime.parse(v.toString(), formatter);
                } catch (Exception e) {
                    System.err.println("Failed to parse participant date: " + v);
                    return LocalDateTime.now();
                }
            });
        });

        model.addAttribute("userEvents", userEvents); // Ваши события
        model.addAttribute("allEvents", allEvents); // Все события

        return "events";
    }

    @GetMapping("/event-info")
    public String showEventDetails(@RequestParam("id") Long eventId, Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/sign-in";
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        Event event = eventService.getEventById(eventId);
        if (event == null) {
            throw new IllegalArgumentException("Event not found");
        }

        // Форматирование участников с датами
        Map<Long, String> formattedParticipants = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        event.getParticipants().forEach((userId, joinTime) -> {
            formattedParticipants.put(userId, joinTime.format(formatter));
        });

        model.addAttribute("event", event);
        model.addAttribute("formattedParticipants", formattedParticipants);

        return "event-info";
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage() {
        return "access-denied";
    }
}