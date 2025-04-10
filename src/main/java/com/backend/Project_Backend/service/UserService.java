package com.backend.Project_Backend.service;

import com.backend.Project_Backend.dto.UserDTO;
import com.backend.Project_Backend.model.User;
import com.backend.Project_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<UserDTO> getUserProfile(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setFullName(user.getFullName());
                    dto.setEmail(user.getEmail());
                    return dto;
                });
    }

    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User deleted";
    }
}
