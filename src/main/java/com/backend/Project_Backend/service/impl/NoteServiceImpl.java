package com.backend.Project_Backend.service.impl;

import com.backend.Project_Backend.model.Note;
import com.backend.Project_Backend.model.User;
import com.backend.Project_Backend.repository.NoteRepository;
import com.backend.Project_Backend.repository.UserRepository;
import com.backend.Project_Backend.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepo;
    private final UserRepository userRepo;
    private final Path uploadDir = Paths.get("uploads");

    @Override
    public Note saveNote(MultipartFile file, String title, String keywords, Long userId) throws Exception {
        Files.createDirectories(uploadDir);
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path target = uploadDir.resolve(filename);
        file.transferTo(target);

        User uploader = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Note note = Note.builder()
                .title(title)
                .keywords(keywords)
                .fileUrl("/files/" + filename)
                .uploader(uploader)
                .build();

        return noteRepo.save(note);
    }

    @Override
    public List<Note> searchNotes(String query) {
        return noteRepo.findByTitleContainingIgnoreCaseOrKeywordsContainingIgnoreCase(query, query);
    }

    @Override
    public List<Note> getRandomNotes(int limit) {
        List<Note> all = noteRepo.findAll();
        Collections.shuffle(all);
        return all.stream().limit(limit).collect(Collectors.toList());
    }

    @Override
    public List<Note> getAllNotes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Note> notePage = noteRepo.findAll(pageable);
        return notePage.getContent();
    }
    @Override
    public List<Note> getNotesByUser(Long userId) {
        return noteRepo.findByUploader_Id(userId);
    }

}