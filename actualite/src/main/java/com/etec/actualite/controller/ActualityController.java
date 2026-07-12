package com.etec.actualite.controller;

import com.etec.actualite.entity.Actuality;
import com.etec.actualite.entity.Categorie;
import com.etec.actualite.entity.Status;
import com.etec.actualite.service.ActualiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/actualites")
@RequiredArgsConstructor
public class ActualityController {

    private final ActualiteService service;

    /**
     * Création d'une actualité
     * Autorisé : ADMIN et SUPER_ADMIN
     */
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(
            @RequestHeader(value = "X-Role", required = false) String role,
            @RequestParam String titre,
            @RequestParam String description,
            @RequestParam Status status,
            @RequestParam Categorie categorie,
            @RequestParam Boolean important,
            @RequestParam MultipartFile file) {

        if (!"ADMIN".equals(role) && !"SUPER_ADMIN".equals(role)) {
            return ResponseEntity.status(403)
                    .body("Accès refusé");
        }

        return ResponseEntity.ok(
                service.save(
                        titre,
                        description,
                        status,
                        categorie,
                        file,
                        important
                )
        );
    }

    /**
     * Consultation de toutes les actualités
     * Accessible à tous
     */
    @GetMapping
    public Page<Actuality> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    /**
     * Consultation d'une actualité
     * Accessible à tous
     */
    @GetMapping("/{id}")
    public Actuality getById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * Modification d'une actualité
     * Autorisé : ADMIN et SUPER_ADMIN
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @RequestHeader(value = "X-Role", required = false) String role,
            @PathVariable Long id,
            @RequestParam String titre,
            @RequestParam String description,
            @RequestParam Status status,
            @RequestParam Categorie categorie,
            @RequestParam Boolean important,
            @RequestParam(required = false) MultipartFile file
    ) {

        if (!"ADMIN".equals(role) && !"SUPER_ADMIN".equals(role)) {
            return ResponseEntity.status(403)
                    .body("Accès refusé");
        }

        return ResponseEntity.ok(
                service.update(
                        id,
                        titre,
                        description,
                        status,
                        categorie,
                        file,
                        important
                )
        );
    }

    /**
     * Suppression d'une actualité
     * Autorisé : SUPER_ADMIN uniquement
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @RequestHeader(value = "X-Role", required = false) String role,
            @PathVariable Long id
    ) {

        if (!"SUPER_ADMIN".equals(role)) {
            return ResponseEntity.status(403)
                    .body("Réservé au Super Administrateur");
        }

        return ResponseEntity.ok(
                service.delete(id)
        );
    }
}