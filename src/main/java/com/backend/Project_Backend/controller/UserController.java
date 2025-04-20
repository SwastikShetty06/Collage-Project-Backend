package com.backend.Project_Backend.controller;

import com.backend.Project_Backend.dto.ChangePasswordRequest;
import com.backend.Project_Backend.dto.UpdateUserDTO;
import com.backend.Project_Backend.dto.UserDTO;
import com.backend.Project_Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public Optional<UserDTO> getProfile(@PathVariable Long id) {
        return userService.getUserProfile(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO dto) {
        String result = userService.updateUser(id, dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Map<String, String>> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request
    ) {
        boolean success = userService.changePassword(id, request.getNewPassword());
        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Password updated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to update password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String result = userService.deleteUser(id);
        return ResponseEntity.ok(result);
    }

    // Endpoint to get all users
    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // Endpoint to search users
    @GetMapping("/search")
    public List<UserDTO> searchUsers(@RequestParam String query) {
        return userService.searchUsers(query);
    }

    // In UserController.java
    @PostMapping("/{followerId}/follow/{followedId}")
    public ResponseEntity<String> follow(@PathVariable Long followerId, @PathVariable Long followedId) {
        boolean result = userService.followUser(followerId, followedId);
        return ResponseEntity.ok(result ? "Followed successfully" : "Failed to follow");
    }

    @DeleteMapping("/{followerId}/unfollow/{followedId}")
    public ResponseEntity<String> unfollow(
            @PathVariable Long followerId,
            @PathVariable Long followedId
    ) {
        try {
            boolean ok = userService.unfollowUser(followerId, followedId);
            if (ok) {
                return ResponseEntity.ok("Unfollowed successfully");
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("No such follow relationship");
            }
        } catch (Exception e) {
            // Print the real exception to your console
            e.printStackTrace();
            // Return a JSON message with the exception message
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error unfollowing user: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<UserDTO>> getFollowedUsers(@PathVariable Long userId) {
        List<UserDTO> followedUsers = userService.getFollowedUsers(userId);
        if (followedUsers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(followedUsers);  // No content if no users are followed
        }
        return ResponseEntity.ok(followedUsers);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<?> getFollowers(@PathVariable Long userId) {
        try {
            List<UserDTO> followers = userService.getFollowers(userId);
            return ResponseEntity.ok(followers);
        } catch (IllegalStateException e) {
            // If no followers, return a custom message
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nobody is following you");
        }
    }
}
