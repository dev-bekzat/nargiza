package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public String handleSignUp(@RequestParam String name, @RequestParam String email, @RequestParam String password, Model model) {
        boolean success = userService.registerUser(name, email, password);

        if (!success) {
            model.addAttribute("error", "Email is already taken!");
            return "sign_up";
        }

        return "redirect:/sign-in"; // Перенаправление на страницу входа после успешной регистрации
    }

    @GetMapping("/home")
    public String homePage(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/sign-in";
        }

        String currentUserEmail = authentication.getName();

        User currentUser = userService.findByEmail(currentUserEmail);

        if (currentUser != null) {
            model.addAttribute("name", currentUser.getName());
        } else {
            model.addAttribute("name", "User");
        }

        return "home";
    }

    @GetMapping("/profile")
    public String getUserProfile(Model model, Principal principal) {
        // Получение текущего пользователя
        String username = principal.getName(); // Получает имя пользователя
        User user = userService.findByEmail(username);

        // Добавление данных пользователя в модель
        model.addAttribute("user", user);
        return "user_profile"; // Возвращает шаблон user_profile.html
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByEmail(username);

        model.addAttribute("user", user);
        return "user_profile_edit"; // Шаблон edit_profile.html
    }

//    @PostMapping("/profile/edit")
//    public String saveProfile(@ModelAttribute User updatedUser, Principal principal) {
//        String username = principal.getName();
//        User user = userService.findByUsername(username);
//
//        // Обновление данных пользователя
//        user.setFirstName(updatedUser.getFirstName());
//        user.setLastName(updatedUser.getLastName());
//        user.setDateOfBirth(updatedUser.getDateOfBirth());
//        user.setMajor(updatedUser.getMajor());
//        user.setCourseNumber(updatedUser.getCourseNumber());
//
//        userService.save(user);
//        return "redirect:/profile"; // Возврат к профилю
//    }
}
