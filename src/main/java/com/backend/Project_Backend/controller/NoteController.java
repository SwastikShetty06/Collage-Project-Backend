package com.backend.Project_Backend.controller;

import com.backend.Project_Backend.dto.NoteDTO;
import com.backend.Project_Backend.model.Note;
import com.backend.Project_Backend.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    // ✅ Upload a new note
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadNote(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("keywords") String keywords,
            @RequestParam("userId") Long userId
    ) {
        try {
            noteService.saveNote(file, title, keywords, userId);
            return ResponseEntity.ok(Collections.singletonMap("message", "Upload successful"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // ✅ Search notes by title or keyword
    @GetMapping("/search")
    public List<Note> search(@RequestParam String query) {
        return noteService.searchNotes(query);
    }

    // ✅ Get a random set of notes (for initial homepage if needed)
    @GetMapping("/random")
    public List<Note> random() {
        return noteService.getRandomNotes(20);
    }

    // ✅ Get all notes paginated
    @GetMapping("/all")
    public ResponseEntity<List<Note>> getAllNotes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Note> notes = noteService.getAllNotes(page, size);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NoteDTO>> getUserDocuments(@PathVariable Long userId) {
        List<Note> notes = noteService.getNotesByUser(userId);
        // map Note→NoteDTO (with title, fileUrl, etc.)
        List<NoteDTO> dtos = notes.stream()
                .map(n -> new NoteDTO(n.getId(), n.getTitle(), n.getFileUrl()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
