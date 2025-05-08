package com.backend.Project_Backend.repository;

import com.backend.Project_Backend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    // Existing full-text search by title or keywords
    List<Note> findByTitleContainingIgnoreCaseOrKeywordsContainingIgnoreCase(String title, String keywords);

    // NEW: fetch all notes uploaded by a given user (matches Note.uploader.id)
    List<Note> findByUploader_Id(Long uploaderId);
}
