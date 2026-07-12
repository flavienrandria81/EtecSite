package com.filiere.filiere.controller;

import com.filiere.filiere.entity.Filiere;
import com.filiere.filiere.service.FiliereService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/filieres")
public class FiliereController {

    private final FiliereService service;


    // Accessible ADMIN seulement
    @PostMapping("/save")
    public ResponseEntity<?> save(
            @RequestHeader("X-Role") String role,
            @RequestBody Filiere filiere
    ) {

        if (!role.equals("ADMIN") && !role.equals("SUPER_ADMIN")) {
            return ResponseEntity.status(403)
                    .body("Accès refusé");
        }

        return ResponseEntity.ok(
                service.save(filiere)
        );
    }

    // Accessible à tous
    @GetMapping
    public Page<Filiere> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }


    // Accessible à tous
    @GetMapping("/{id}")
    public Filiere getById(@PathVariable Long id) {
        return service.findById(id);
    }


    // Accessible ADMIN seulement
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @RequestHeader("X-Role") String role,
            @PathVariable Long id,
            @RequestBody Filiere filiere
    ) {

        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(403)
                    .body("Vous n'avez pas l'autorisation");
        }

        return ResponseEntity.ok(
                service.update(id, filiere)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @RequestHeader("X-Role") String role,
            @PathVariable Long id
    ) {

        if (!role.equals("SUPER_ADMIN")) {
            return ResponseEntity.status(403)
                    .body("Réservé au Super Administrateur");
        }

        service.delete(id);

        return ResponseEntity.ok(
                "Filière supprimée"
        );
    }
}