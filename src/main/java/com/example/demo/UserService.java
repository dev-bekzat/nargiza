package com.example.demo;

import com.example.demo.User;
import com.example.demo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean registerUser(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            return false; // email занят
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password); // В будущем добавлю хеширование
        userRepository.save(user);
        return true;
    }

    public boolean authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(password);
    }
}
