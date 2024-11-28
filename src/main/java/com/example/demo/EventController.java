package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    @GetMapping("/events")
    public String showEventsPage(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/sign-in"; // Перенаправить на страницу входа
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        List<Event> userEvents = eventService.getEventsByUserId(user.getId()); // События пользователя

        model.addAttribute("userEvents", userEvents); // Ваши события

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

        model.addAttribute("event", event);
        return "event-info";
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage() {
        return "access-denied";
    }
}