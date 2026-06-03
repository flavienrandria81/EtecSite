package com.example.Etudiant.demo.Controller;

import com.example.Etudiant.demo.Entity.Etudiant;
import com.example.Etudiant.demo.Service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Etudiants")
@CrossOrigin(origins = "*")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    // ── GET /api/etudiants ────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        return ResponseEntity.ok(etudiantService.getAllEtudiants());
    }

    // ── GET /api/etudiants/{id} ───────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> getEtudiantById(@PathVariable Long id) {
        return etudiantService.getEtudiantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── GET /api/etudiants/email/{email} ──────────────────────────────────────
    @GetMapping("/email/{email}")
    public ResponseEntity<Etudiant> getEtudiantByEmail(@PathVariable String email) {
        return etudiantService.getEtudiantByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── GET /api/etudiants/matricule/{matricule} ──────────────────────────────
    @GetMapping("/matricule/{matricule}")
    public ResponseEntity<Etudiant> getEtudiantByMatricule(@PathVariable String matricule) {
        return etudiantService.getEtudiantByMatricule(matricule)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── POST /api/etudiants ───────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<?> createEtudiant(@RequestBody Etudiant etudiant) {
        try {
            Etudiant created = etudiantService.createEtudiant(etudiant);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // ── PUT /api/etudiants/{id} ───────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEtudiant(@PathVariable Long id,
                                            @RequestBody Etudiant etudiant) {
        try {
            Etudiant updated = etudiantService.updateEtudiant(id, etudiant);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ── DELETE /api/etudiants/{id} ────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEtudiant(@PathVariable Long id) {
        try {
            etudiantService.deleteEtudiant(id);
            return ResponseEntity.ok("Étudiant supprimé avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}