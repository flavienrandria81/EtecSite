package com.memoire.memoire.controller;

import com.memoire.memoire.entity.Memoire;
import com.memoire.memoire.entity.StatutMemoire;
import com.memoire.memoire.service.MemoireService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/memoires")
@RequiredArgsConstructor
public class MemoireController {

    private final MemoireService service;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Memoire save(
            @RequestParam Long etudiantId,
            @RequestParam Long userId,
            @RequestParam Long noteId,
            @RequestParam Long enseignantId,
            @RequestParam Long filiereId,
            @RequestParam Long niveauId,
            @RequestParam Long domaineId,
            @RequestParam String theme,
            @RequestParam String description,
            @RequestParam StatutMemoire statutMemoire,
            @RequestParam("livre")MultipartFile livre
            ) {
        return service.save(etudiantId,
                userId,
                noteId,
                enseignantId,
                filiereId,
                niveauId,
                domaineId,
                theme,
                description,
                statutMemoire,
                livre);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Page<Memoire> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Memoire getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Memoire update(
            @PathVariable Long id,
            @RequestParam String theme,
            @RequestParam String description,
            @RequestParam StatutMemoire statutMemoire,
            @RequestParam(value = "livre", required = false) MultipartFile livre
    ) {
        return service.update(id, theme, description, statutMemoire, livre);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
