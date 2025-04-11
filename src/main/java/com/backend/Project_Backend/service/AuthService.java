package com.backend.Project_Backend.service;

import com.backend.Project_Backend.dto.AuthRequestDTO;
import com.backend.Project_Backend.dto.UserDTO;
import com.backend.Project_Backend.model.User;
import com.backend.Project_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    // Registration remains unchanged
    public String register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "User already exists";
        }
        userRepository.save(user);
        return "Registration successful";
    }

    public UserDTO login(AuthRequestDTO dto) {
        return userRepository.findByEmail(dto.getEmail())
                .filter(user -> user.getPassword().equals(dto.getPassword()))
                .map(user -> {
                    UserDTO responseDto = new UserDTO();
                    responseDto.setId(user.getId());            // Set the ID
                    responseDto.setFullName(user.getFullName());
                    responseDto.setEmail(user.getEmail());
                    return responseDto;
                })
                .orElse(null); // Return null if authentication fails
    }
}
