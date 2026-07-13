package com.dom.domaine.controller;

import com.dom.domaine.entity.Domaine;
import com.dom.domaine.service.DomaineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/domaines")
public class DomaineController {

    private final DomaineService service;

    @PostMapping("/save")
    public ResponseEntity<?> save(
            @RequestHeader("X-Role") String role,
            @RequestBody Domaine domaine
    ) {

        if (!role.equals("ADMIN") && !role.equals("SUPER_ADMIN")) {
            return ResponseEntity.status(403)
                    .body("Accès refusé");
        }

        return ResponseEntity.ok(
                service.save(domaine)
        );
    }
    @GetMapping
    public Page<Domaine> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Domaine getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @RequestHeader("X-Role") String role,
            @PathVariable Long id,
            @RequestBody Domaine domaine
    ) {

        if (!role.equals("ADMIN") && !role.equals("SUPER_ADMIN")) {
            return ResponseEntity.status(403)
                    .body("Vous n'avez pas l'autorisation");
        }

        return ResponseEntity.ok(
                service.update(id, domaine)
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
