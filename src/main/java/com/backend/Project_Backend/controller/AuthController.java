package com.backend.Project_Backend.controller;

import com.backend.Project_Backend.dto.AuthRequestDTO;
import com.backend.Project_Backend.dto.UserDTO;
import com.backend.Project_Backend.model.User;
import com.backend.Project_Backend.service.AuthService;
import com.backend.Project_Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        String result = authService.register(user);
        // Instead of returning a plain string, return JSON:
        return Collections.singletonMap("message", result);
    }


    @PostMapping("/login")
    public Object login(@RequestBody AuthRequestDTO dto) {
        UserDTO user = authService.login(dto);
        if (user == null) {
            return "Invalid email or password";
        }
        return user;
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
