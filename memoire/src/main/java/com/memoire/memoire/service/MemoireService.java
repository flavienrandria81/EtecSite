package com.memoire.memoire.service;

import com.memoire.memoire.entity.Memoire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface MemoireService {
    Memoire save(String theme, String description, MultipartFile file);
    Page<Memoire> findAll(Pageable pageable);
    Memoire findById(Long id);
    Memoire update(Long id, String theme, String description, MultipartFile file);
    String delete(Long id);
}
