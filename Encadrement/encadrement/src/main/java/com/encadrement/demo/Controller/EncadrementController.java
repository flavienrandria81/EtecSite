package com.encadrement.demo.Controller;

import com.encadrement.demo.Entity.Encadrement;
import com.encadrement.demo.Service.EncadrementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Encadrements")
public class EncadrementController {

    @Autowired
    private EncadrementService encadrementService;

    @GetMapping
    public ResponseEntity<List<Encadrement>> getAllEncadrements() {
        List<Encadrement> encadrements = encadrementService.getAllEncadrements();
        return ResponseEntity.ok(encadrements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Encadrement> getEncadrementById(@PathVariable Long id) {
        Optional<Encadrement> encadrement = encadrementService.getEncadrementById(id);
        return encadrement.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Encadrement> createEncadrement(@RequestBody Encadrement encadrement) {
        Encadrement savedEncadrement = encadrementService.createEncadrement(encadrement);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEncadrement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Encadrement> updateEncadrement(@PathVariable Long id, @RequestBody Encadrement encadrement) {
        Encadrement updatedEncadrement = encadrementService.updateEncadrement(id, encadrement);
        return ResponseEntity.ok(updatedEncadrement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEncadrement(@PathVariable Long id) {
        encadrementService.deleteEncadrement(id);
        return ResponseEntity.noContent().build();
    }
}