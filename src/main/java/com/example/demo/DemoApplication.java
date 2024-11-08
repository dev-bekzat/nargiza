package com.example.demo;

import com.example.demo.User;
import com.example.demo.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Controller
	public class HomeController {

		@Autowired
		private UserService userService;

		@GetMapping("/")
		public String home() {
			return "index";
		}

		@PostMapping("/register")
		public String register(
				@RequestParam String username,
				@RequestParam String password,
				@RequestParam String phone,
				RedirectAttributes redirectAttributes) {
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setPhone(phone);

			userService.registerUser(user);
			redirectAttributes.addFlashAttribute("success", "Registration successful!");
			return "redirect:/secondPage";
		}

		@GetMapping("/secondPage")
		public String secondPage() {
			return "secondPage";
		}
	}
}
