package com.example.Enseignant.demo.Controller;

import com.example.Enseignant.demo.Entity.Enseignant;
import com.example.Enseignant.demo.Service.EnseignantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/Enseignants")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor // Gère automatiquement l'injection du service
public class EnseignantController {

    private final EnseignantService enseignantService;


    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Enseignant create(@RequestParam String matricule,
                           @RequestParam String cin,
                           @RequestParam String adresse,
                           @RequestParam String phone,
                           @RequestParam String specialite,
                           @RequestParam Long filiereId,
                           @RequestParam Long matiereId,
                           @RequestParam("photo") MultipartFile photo,
                           @RequestParam("diplome") MultipartFile diplome,
                           HttpServletRequest request) {

        return enseignantService.createEnseignant(matricule, cin, adresse, phone, specialite, filiereId, matiereId, photo, diplome, request);
    }
    // GET /api/enseignants
    @GetMapping
    public Page<Enseignant> getAll(Pageable pageable) {
        return enseignantService.getAllEnseignants(pageable);
    }

    // GET /api/enseignants/{id}
    @GetMapping("/{id}")
    public Enseignant getEnseignantById(@PathVariable Long id) {
        return enseignantService.getEnseignantById(id);
    }

    @GetMapping("/matricule/{matricule}")
    public Enseignant getByMatricule(@PathVariable String matricule) {
        return enseignantService.getByMatricule(matricule);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Enseignant update(@PathVariable Long id,
                           @RequestParam String matricule,
                           @RequestParam String cin,
                           @RequestParam String adresse,
                           @RequestParam String phone,
                           @RequestParam String specialite,
                           @RequestParam(value = "photo", required = false) MultipartFile photo,
                           @RequestParam(value = "diplome", required = false) MultipartFile diplome
    ) {
        return enseignantService.updateEnseignant(id, matricule, cin, adresse, phone, specialite, photo, diplome);
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