package com.example.demo;

import com.example.demo.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public String handleSignUp(@RequestParam String name, @RequestParam String email, @RequestParam String password, Model model) {
        boolean success = userService.registerUser(name, email, password);

        if (!success) {
            model.addAttribute("error", "Email is already taken!");
            return "sign_in";
        }

        return "redirect:/home";
    }

    @PostMapping("/sign-in")
    public String handleSignIn(@RequestParam String email, @RequestParam String password, Model model) {
        boolean authenticated = userService.authenticateUser(email, password);

        if (!authenticated) {
            model.addAttribute("error", "Invalid email or password!");
            return "sign_up";
        }

        return "redirect:/home";
    }
}
