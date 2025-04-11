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
                    dto.setId(user.getId());
                    dto.setFullName(user.getFullName());
                    dto.setEmail(user.getEmail());
                    return dto;
                });
    }


    public String updateUser(Long id, UserDTO updated) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) return "User not found";

        User user = userOpt.get();
        user.setFullName(updated.getFullName());
        user.setEmail(updated.getEmail());
        userRepository.save(user);
        return "User updated successfully";
    }

    public String changePassword(Long id, String newPassword) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) return "User not found";

        User user = userOpt.get();
        user.setPassword(newPassword); // In real apps, hash it
        userRepository.save(user);
        return "Password changed successfully";
    }

    public String deleteUser(Long id) {
        if (!userRepository.existsById(id)) return "User not found";
        userRepository.deleteById(id);
        return "User deleted";
    }
}
