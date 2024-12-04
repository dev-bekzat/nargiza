package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalTime time;

    private String imagePath; // Убедитесь, что поле объявлено

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getImagePath() { // Обязательно добавьте геттер
        return imagePath;
    }

    public void setImagePath(String imagePath) { // И сеттер
        this.imagePath = imagePath;
    }

    @ElementCollection
    @CollectionTable(name = "event_participants", joinColumns = @JoinColumn(name = "event_id"))
    @MapKeyColumn(name = "participant_id") // Колонка для ID участника
    @Column(name = "joined_at") // Колонка для времени
    private Map<Long, LocalDateTime> participants = new HashMap<>();

    public Map<Long, LocalDateTime> getParticipants() {
        return participants; // Возвращаем карту участников
    }

    public List<Long> getParticipantIds() {
        return new ArrayList<>(participants.keySet());
    }

    public void setParticipants(Map<Long, LocalDateTime> participants) {
        this.participants = participants;
    }

    public void addParticipant(Long userId) {
        if (!participants.containsKey(userId)) {
            participants.put(userId, LocalDateTime.now()); // Добавляем текущую дату и время
        }
    }
}