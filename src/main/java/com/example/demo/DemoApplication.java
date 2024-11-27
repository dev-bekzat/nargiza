package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication(scanBasePackages = "com.example.demo")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Controller
	public static class PageController {

		@GetMapping("/")
		public String showFirstPage() {
			return "firstpage"; // Начальная страница
		}

//		@GetMapping("/home")
//		public String showHomePage() {
//			return "home"; // Главная страница SDU Friends
//		}

		@GetMapping("/events")
		public String showEventsPage() {
			return "events"; // Страница предстоящих событий
		}

		@GetMapping("/event-info")
		public String showEventInfoPage() {
			return "event-info"; // Информация о событии
		}

		@GetMapping("/create")
		public String showCreateEventPage() {
			return "create"; // Страница создания события
		}

		@GetMapping("/create-post")
		public String showCreatePostPage() {
			return "create_post"; // Страница создания поста
		}

		@GetMapping("/profile")
		public String showUserProfilePage() {
			return "user_profile"; // Профиль пользователя
		}

		@GetMapping("/profile/edit")
		public String showEditUserProfilePage() {
			return "user_profile_edit"; // Страница редактирования профиля пользователя
		}

		@GetMapping("/trending-posts")
		public String showTrendingPostsPage() {
			return "trending_posts"; // Страница с трендовыми постами
		}

		@GetMapping("/sign-up")
		public String showSignUpPage() {
			return "sign_up"; // Страница регистрации
		}

		@GetMapping("/sign-in")
		public String showSignInPage() {
			return "sign_in"; // Страница регистрации
		}

		@GetMapping("/post-info")
		public String showPostInfoPage() {
			return "post_info"; // Страница информации о посте
		}

//		@PostMapping("/sign-up")
//		public String handleSignUp() {
//			return "redirect:/profile"; // Перенаправление на страницу профиля
//		}
	}
}
