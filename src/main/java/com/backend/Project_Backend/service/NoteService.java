package com.backend.Project_Backend.service;

import com.backend.Project_Backend.model.Note;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface NoteService {
    Note saveNote(MultipartFile file, String title, String keywords, Long userId) throws Exception;
    List<Note> searchNotes(String query);
    List<Note> getRandomNotes(int limit);
    List<Note> getAllNotes(int page, int size);
    List<Note> getNotesByUser(Long userId);

}