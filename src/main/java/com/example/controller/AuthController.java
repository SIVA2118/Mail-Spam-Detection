package com.example.controller;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return Map.of("status", "error", "message", "User already exists");
        }
        userRepository.save(user);
        return Map.of("status", "success", "message", "User registered successfully");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser.isPresent() && foundUser.get().getPassword().equals(user.getPassword())) {
            return Map.of("status", "success", "message", "Login successful", "username", user.getUsername());
        }
        return Map.of("status", "error", "message", "Invalid credentials");
    }

    @GetMapping("/profile")
    public Map<String, Object> getProfile(@RequestParam String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            User u = user.get();
            return Map.of(
                "status", "success",
                "username", u.getUsername(),
                "email", u.getEmail() != null ? u.getEmail() : "",
                "fullName", u.getFullName() != null ? u.getFullName() : "",
                "phoneNumber", u.getPhoneNumber() != null ? u.getPhoneNumber() : ""
            );
        }
        return Map.of("status", "error", "message", "User not found");
    }

    @PostMapping("/update")
    public Map<String, String> updateProfile(@RequestBody User updatedUser) {
        Optional<User> userOpt = userRepository.findByUsername(updatedUser.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setFullName(updatedUser.getFullName());
            user.setEmail(updatedUser.getEmail());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            userRepository.save(user);
            return Map.of("status", "success", "message", "Profile updated successfully");
        }
        return Map.of("status", "error", "message", "User not found");
    }
}
