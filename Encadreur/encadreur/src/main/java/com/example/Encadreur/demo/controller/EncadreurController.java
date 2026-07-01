package com.example.Encadreur.demo.controller;

import com.example.Encadreur.demo.entity.Encadreur;
import com.example.Encadreur.demo.service.EncadreurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encadreurs")
public class EncadreurController {

    private final EncadreurService encadreurService;

    public EncadreurController(EncadreurService encadreurService) {
        this.encadreurService = encadreurService;
    }

    // ── Récupérer tous ──────────────────────────────────────
    @GetMapping
    public List<Encadreur> getAll() {
        return encadreurService.getAll();
    }

    // ── Récupérer par ID ────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<Encadreur> getById(@PathVariable Long id) {
        return encadreurService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── Créer ───────────────────────────────────────────────
    @PostMapping
    public Encadreur create(@RequestBody Encadreur encadreur) {
        return encadreurService.save(encadreur);
    }

    // ── Modifier ────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<Encadreur> update(@PathVariable Long id,
                                            @RequestBody Encadreur encadreur) {
        return encadreurService.getById(id)
                .map(existing -> {
                    encadreur.setId_encadreur(id);
                    return ResponseEntity.ok(encadreurService.save(encadreur));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ── Supprimer ───────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return encadreurService.getById(id)
                .map(existing -> {
                    encadreurService.delete(id);
                    return ResponseEntity.<Void>ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}