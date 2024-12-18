package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Controller
	public static class HomeController {

		@GetMapping("/")
		public String home() {
			return "index"; // возвращает index.html
		}

		@PostMapping("/login")
		public String login() {
			System.out.println("Login form submitted");
			return "redirect:/secondPage"; // перенаправляет на /secondPage
		}

		@GetMapping("/secondPage")
		public String secondPage() {
			return "secondPage"; // возвращает secondPage.html
		}
	}
}
