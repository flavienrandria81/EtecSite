package com.etudiant.note.service;

import com.etudiant.note.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface NoteService {
    Note save(Note note);
    Page<Note> findAll(Pageable pageable);
    Note findById(Long id);
    Note update(Long id, Note note);
    void delete(Long id);
    List<Note> getByEtudiant(Long etudiantId);
    Map<String, Object> getNoteComplet(Long id);
}
