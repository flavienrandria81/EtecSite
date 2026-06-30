package com.etudiant.note.controller;

import com.etudiant.note.entity.Note;
import com.etudiant.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/save")
    public Note save(@RequestBody Note note) {
        return noteService.save(note);
    }

    @GetMapping
    public Page<Note> getAll(Pageable pageable) {
        return noteService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Note getById(@PathVariable Long id) {
        return noteService.findById(id);
    }

    @PutMapping("/{id}")
    public Note update(@PathVariable Long id,
                       @RequestBody Note note) {
        return noteService.update(id,note);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        noteService.delete(id);
    }

    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Note>> getByEtudiant(
            @PathVariable Long etudiantId) {

        return ResponseEntity.ok(
                noteService.getByEtudiant(etudiantId));
    }

    @GetMapping("/complet/{id}")
    public Map<String, Object> getComplet(@PathVariable Long id) {
        return noteService.getNoteComplet(id);
    }

}
