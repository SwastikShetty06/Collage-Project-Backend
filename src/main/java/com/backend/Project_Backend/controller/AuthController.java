package com.backend.Project_Backend.controller;

import com.backend.Project_Backend.dto.AuthRequestDTO;
import com.backend.Project_Backend.dto.UserDTO;
import com.backend.Project_Backend.model.User;
import com.backend.Project_Backend.service.AuthService;
import com.backend.Project_Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDTO dto) {
        return authService.login(dto);
    }

    @GetMapping("/profile/{id}")
    public Optional<UserDTO> getProfile(@PathVariable Long id) {
        return userService.getUserProfile(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
