package com.etec.historique.controller;

import com.etec.historique.entity.Historique;
import com.etec.historique.service.HistoireService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/histoires")
@RequiredArgsConstructor
public class HistoireController {

    private final HistoireService histoireService;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Historique save(
            @RequestParam String nom,
            @RequestParam String description,
            @RequestParam MultipartFile file
            ) {
        return histoireService.save(nom, description, file);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Page<Historique> getAll(Pageable pageable) {
        return histoireService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Historique getById(@PathVariable Long id) {
        return histoireService.findById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Historique update(
            @PathVariable Long id,
            @RequestParam String nom,
            @RequestParam String description,
            @RequestParam (required = false) MultipartFile file
    ) {
        return histoireService.update(id, nom, description, file);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(histoireService.delete(id));
    }
}
