package com.backend.Project_Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String keywords;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "uploader_id")
    @JsonBackReference
    private User uploader;
}
