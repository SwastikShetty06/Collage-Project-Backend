package com.backend.Project_Backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "college_name")
    private String collegeName;

    @Column(name = "university_name")
    private String universityName;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "best_friend_name")
    private String bestFriendName;

    @OneToMany(mappedBy = "uploader", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Only serialize from User → Notes
    private List<Note> notes = new ArrayList<>();
}
