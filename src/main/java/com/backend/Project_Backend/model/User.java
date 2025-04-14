package com.backend.Project_Backend.model;

import jakarta.persistence.*;
import lombok.*;

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

    // New profile fields:
    @Column(name = "college_name")
    private String collegeName;

    @Column(name = "university_name")
    private String universityName;

    @Column(name = "course_name")
    private String courseName;
}
