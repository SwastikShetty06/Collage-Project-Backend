package com.backend.Project_Backend.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String collegeName;
    private String universityName;
    private String courseName;
}
