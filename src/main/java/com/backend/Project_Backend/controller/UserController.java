package com.backend.Project_Backend.controller;

import com.backend.Project_Backend.dto.ChangePasswordRequest;
import com.backend.Project_Backend.dto.UserDTO;
import com.backend.Project_Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.backend.Project_Backend.model.User;
import com.backend.Project_Backend.repository.UserRepository;
import com.backend.Project_Backend.dto.ChangePasswordRequest; // Also import your DTO class


import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    @GetMapping("/{id}")
    public Optional<UserDTO> getProfile(@PathVariable Long id) {
        return userService.getUserProfile(id);
    }

    @PutMapping("/{id}")
    public String updateProfile(@PathVariable Long id, @RequestBody UserDTO updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @PutMapping("/{id}/password")
    public String changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return "User not found";
        }

        User user = userOpt.get();
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return "Password changed successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}

