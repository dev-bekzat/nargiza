package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Controller
	public static class HomeController {

		@GetMapping("/")
		public String home() {
			return "firstpage"; // Главная страница для входа
		}

		@GetMapping("/home")
		public String homePage() {
			return "home"; // Основная страница SDU Friends
		}

		@GetMapping("/events")
		public String events() {
			return "events"; // Страница со списком событий
		}

		@GetMapping("/event-info")
		public String eventInfo() {
			return "event-info"; // Страница с подробной информацией о событии
		}

		@GetMapping("/create")
		public String createEvent() {
			return "create"; // Страница для создания нового события
		}

		@GetMapping("/profile")
		public String profile() {
			return "htmlthree"; // Профиль пользователя
		}
	}
}
