package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
