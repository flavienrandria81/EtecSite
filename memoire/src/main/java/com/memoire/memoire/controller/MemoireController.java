package com.memoire.memoire.controller;

import com.memoire.memoire.entity.Memoire;
import com.memoire.memoire.service.MemoireService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/memoires")
@RequiredArgsConstructor
public class MemoireController {

    private final MemoireService service;

    @PostMapping(name = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Memoire save(
            @RequestParam String theme,
            @RequestParam String description,
            @RequestParam("livre")MultipartFile livre
            ) {
        return service.save(theme, description, livre);
    }

    @GetMapping
    public Page<Memoire> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Memoire getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Memoire update(
            @PathVariable Long id,
            @RequestParam String theme,
            @RequestParam String description,
            @RequestParam(value = "livre", required = false) MultipartFile livre
    ) {
        return service.update(id, theme, description, livre);
    }

    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
