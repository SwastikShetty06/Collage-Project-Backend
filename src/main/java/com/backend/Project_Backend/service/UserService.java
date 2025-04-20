package com.backend.Project_Backend.service;

import com.backend.Project_Backend.dto.UserDTO;
import com.backend.Project_Backend.dto.UpdateUserDTO;
import com.backend.Project_Backend.model.Follow;
import com.backend.Project_Backend.model.User;
import com.backend.Project_Backend.repository.FollowRepository;
import com.backend.Project_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

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

    public boolean changePassword(Long id, String newPassword) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) return false;

        User user = userOpt.get();
        user.setPassword(newPassword);
        userRepository.save(user);
        return true;
    }

    public boolean forgotPassword(String email, String bestFriendName, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getBestFriendName() != null && user.getBestFriendName().equalsIgnoreCase(bestFriendName)) {
                user.setPassword(newPassword);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    public String deleteUser(Long id) {
        if (!userRepository.existsById(id)) return "User not found";
        userRepository.deleteById(id);
        return "User deleted";
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setFullName(user.getFullName());
            dto.setEmail(user.getEmail());
            dto.setCollegeName(user.getCollegeName());
            dto.setUniversityName(user.getUniversityName());
            dto.setCourseName(user.getCourseName());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<UserDTO> searchUsers(String query) {
        return userRepository.findAll().stream()
                .filter(user ->
                        (user.getFullName() != null && user.getFullName().contains(query)) ||
                                (user.getEmail() != null && user.getEmail().contains(query)) ||
                                (user.getUniversityName() != null && user.getUniversityName().contains(query)) ||
                                (user.getCollegeName() != null && user.getCollegeName().contains(query)) ||
                                (user.getCourseName() != null && user.getCourseName().contains(query))
                )
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setFullName(user.getFullName());
                    dto.setEmail(user.getEmail());
                    dto.setCollegeName(user.getCollegeName());
                    dto.setUniversityName(user.getUniversityName());
                    dto.setCourseName(user.getCourseName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public boolean followUser(Long followerId, Long followedId) {
        User follower = userRepository.findById(followerId).orElse(null);
        User followed = userRepository.findById(followedId).orElse(null);
        if (follower == null || followed == null) return false;

        if (followRepository.findByFollowerAndFollowed(follower, followed).isEmpty()) {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowed(followed);
            followRepository.save(follow);
        }
        return true;
    }

    @Transactional
    public boolean unfollowUser(Long followerId, Long followedId) {
        User follower = userRepository.findById(followerId).orElse(null);
        User followed = userRepository.findById(followedId).orElse(null);
        if (follower == null || followed == null) return false;

        followRepository.deleteByFollowerAndFollowed(follower, followed);
        return true;
    }

    public List<UserDTO> getFollowedUsers(Long userId) {
        User follower = userRepository.findById(userId).orElse(null);
        if (follower == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<Follow> follows = followRepository.findByFollower(follower);
        if (follows.isEmpty()) {
            return List.of();
        }

        return follows.stream()
                .map(f -> {
                    User followedUser = f.getFollowed();
                    UserDTO dto = new UserDTO();
                    dto.setId(followedUser.getId());
                    dto.setFullName(followedUser.getFullName());
                    dto.setEmail(followedUser.getEmail());
                    dto.setCollegeName(followedUser.getCollegeName());
                    dto.setUniversityName(followedUser.getUniversityName());
                    dto.setCourseName(followedUser.getCourseName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<UserDTO> getFollowers(Long userId) {
        User followed = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Fetch all users who follow the "followed" user
        List<Follow> follows = followRepository.findByFollowed(followed);

        if (follows.isEmpty()) {
            throw new IllegalStateException("Nobody is following you");
        }

        return follows.stream()
                .map(f -> {
                    User follower = f.getFollower();  // This is the user who is following
                    UserDTO dto = new UserDTO();
                    dto.setId(follower.getId());
                    dto.setFullName(follower.getFullName());
                    dto.setEmail(follower.getEmail());
                    dto.setCollegeName(follower.getCollegeName());
                    dto.setUniversityName(follower.getUniversityName());
                    dto.setCourseName(follower.getCourseName());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
