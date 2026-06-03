package com.example.Enseignant.demo.Controller;

import com.example.Enseignant.demo.Entity.Enseignant;
import com.example.Enseignant.demo.Service.EnseignantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Enseignants")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor // Gère automatiquement l'injection du service
public class EnseignantController {

    private final EnseignantService enseignantService;

    // GET /api/enseignants
    @GetMapping
    public ResponseEntity<List<Enseignant>> getAllEnseignants() {
        return ResponseEntity.ok(enseignantService.getAllEnseignants());
    }

    // GET /api/enseignants/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Enseignant> getEnseignantById(@PathVariable Long id) {
        return enseignantService.getEnseignantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/enseignants/mail/{mail}
    @GetMapping("/mail/{mail}")
    public ResponseEntity<Enseignant> getEnseignantByMail(@PathVariable String mail) {
        return enseignantService.getEnseignantByMail(mail)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/enseignants
    @PostMapping
    public ResponseEntity<?> createEnseignant(@Valid @RequestBody Enseignant enseignant) {
        try {
            Enseignant created = enseignantService.createEnseignant(enseignant);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }

    // PUT /api/enseignants/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnseignant(@PathVariable Long id, @Valid @RequestBody Enseignant enseignant) {
        try {
            Enseignant updated = enseignantService.updateEnseignant(id, enseignant);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // DELETE /api/enseignants/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnseignant(@PathVariable Long id) {
        try {
            enseignantService.deleteEnseignant(id);
            return ResponseEntity.ok(Map.of("message", "Enseignant supprimé avec succès."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}