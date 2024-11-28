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
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        List<Event> events = eventService.getEventsByUserId(user.getId());
        model.addAttribute("events", events);

        return "events";
    }
}