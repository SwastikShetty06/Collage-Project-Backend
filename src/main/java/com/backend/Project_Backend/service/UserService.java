package com.backend.Project_Backend.service;

import com.backend.Project_Backend.dto.UserDTO;
import com.backend.Project_Backend.dto.UpdateUserDTO;
import com.backend.Project_Backend.model.User;
import com.backend.Project_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Fetch complete profile information
    public Optional<UserDTO> getUserProfile(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setFullName(user.getFullName());
                    dto.setEmail(user.getEmail());
                    dto.setCollegeName(user.getCollegeName());
                    dto.setUniversityName(user.getUniversityName());
                    dto.setCourseName(user.getCourseName());
                    return dto;
                });
    }

    // Update profile fields (collegeName, universityName, courseName)
    public String updateUser(Long id, UpdateUserDTO updated) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) return "User not found";

        User user = userOpt.get();

        if (updated.getCollegeName() != null && !updated.getCollegeName().isBlank()) {
            user.setCollegeName(updated.getCollegeName());
        }
        if (updated.getUniversityName() != null && !updated.getUniversityName().isBlank()) {
            user.setUniversityName(updated.getUniversityName());
        }
        if (updated.getCourseName() != null && !updated.getCourseName().isBlank()) {
            user.setCourseName(updated.getCourseName());
        }
        userRepository.save(user);
        return "User updated successfully";
    }

    // Update the password in plain text (security configuration removed)
    public boolean changePassword(Long id, String newPassword) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) return false;

        User user = userOpt.get();
        user.setPassword(newPassword);
        userRepository.save(user);
        return true;
    }

    // Delete user by ID
    public String deleteUser(Long id) {
        if (!userRepository.existsById(id)) return "User not found";
        userRepository.deleteById(id);
        return "User deleted";
    }
}
