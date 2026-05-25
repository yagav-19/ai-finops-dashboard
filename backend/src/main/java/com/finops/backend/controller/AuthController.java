package com.finops.backend.controller;

import com.finops.backend.model.User;
import com.finops.backend.repository.UserRepository;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public String signup(
            @RequestBody User user) {

        if (userRepository
                .findByUsername(
                        user.getUsername()
                )
                .isPresent()) {

            return "Username already exists";
        }

        userRepository.save(user);

        return "Signup successful";
    }

    @PostMapping("/login")
    public String login(
            @RequestBody User user) {

        return userRepository
                .findByUsername(
                        user.getUsername()
                )
                .filter(
                        existing ->
                                existing
                                        .getPassword()
                                        .equals(
                                                user.getPassword()
                                        )
                )
                .map(
                        existing ->
                                "Login successful"
                )
                .orElse(
                        "Invalid username or password"
                );
    }
}