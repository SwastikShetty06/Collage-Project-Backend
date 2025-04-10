package com.backend.Project_Backend.service;

import com.backend.Project_Backend.dto.AuthRequestDTO;
import com.backend.Project_Backend.model.User;
import com.backend.Project_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public String register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "User already exists";
        }
        userRepository.save(user);
        return "Registration successful";
    }

    public String login(AuthRequestDTO dto) {
        return userRepository.findByEmail(dto.getEmail())
                .filter(user -> user.getPassword().equals(dto.getPassword()))
                .map(user -> "Login successful")
                .orElse("Invalid email or password");
    }
}
