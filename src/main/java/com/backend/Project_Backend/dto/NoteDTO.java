package com.backend.Project_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoteDTO {
    private Long id;
    private String title;
    private String fileUrl;
}
