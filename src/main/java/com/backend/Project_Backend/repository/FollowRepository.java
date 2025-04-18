// com.backend.Project_Backend.repository.FollowRepository.java
package com.backend.Project_Backend.repository;

import com.backend.Project_Backend.model.Follow;
import com.backend.Project_Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollower(User follower);  // Finds all follows by the given follower (userId)
    Optional<Follow> findByFollowerAndFollowed(User follower, User followed);
    void deleteByFollowerAndFollowed(User follower, User followed);
}

