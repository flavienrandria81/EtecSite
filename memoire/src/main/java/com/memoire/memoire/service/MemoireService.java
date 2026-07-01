package com.memoire.memoire.service;

import com.memoire.memoire.entity.Memoire;
import com.memoire.memoire.entity.StatutMemoire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface MemoireService {
    Memoire save(Long etudiantId, Long userId, Long noteId, Long enseignantId, Long filiereId, Long niveauId, Long domaineId, String theme, String description, StatutMemoire statutMemoire, MultipartFile file);
    Page<Memoire> findAll(Pageable pageable);
    Memoire findById(Long id);
    Memoire update(Long id, String theme, String description, StatutMemoire statutMemoire, MultipartFile file);
    String delete(Long id);
}
