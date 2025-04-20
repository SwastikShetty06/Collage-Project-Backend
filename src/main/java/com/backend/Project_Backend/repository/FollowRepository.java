package com.backend.Project_Backend.repository;

import com.backend.Project_Backend.model.Follow;
import com.backend.Project_Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // Finds all follows by the given follower (userId)
    List<Follow> findByFollower(User follower);

    // Finds if a specific user is following another specific user
    Optional<Follow> findByFollowerAndFollowed(User follower, User followed);

    // Deletes a follow relationship between two users
    void deleteByFollowerAndFollowed(User follower, User followed);

    // Finds all the users who are being followed by the given user (followers)
    List<Follow> findByFollowed(User followed);  // This finds users who follow this user
}
