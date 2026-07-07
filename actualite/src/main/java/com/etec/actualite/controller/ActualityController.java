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

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Actuality save(
            @RequestParam String titre,
            @RequestParam String description,
            @RequestParam Status status,
            @RequestParam Categorie categorie,
            @RequestParam Boolean important,
            @RequestParam MultipartFile file) {

        return service.save(titre, description, status, categorie, file, important);
    }

    @GetMapping
    public Page<Actuality> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Actuality getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Actuality update(
            @PathVariable Long id,
            @RequestParam String titre,
            @RequestParam String description,
            @RequestParam Status status,
            @RequestParam Categorie categorie,
            @RequestParam Boolean important,
            @RequestParam(required = false) MultipartFile file
    ) {
        return service.update(id, titre, description, status, categorie, file, important);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
