package com.etudiant.empoiDuTemps.controller;

import com.etudiant.empoiDuTemps.entity.EmploiDuTemps;
import com.etudiant.empoiDuTemps.service.EmploiDuTempsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/emploiDuTemps")
@RequiredArgsConstructor
public class EmploiDuTempsController {

    private final EmploiDuTempsService service;

    @PostMapping("/save")
    public EmploiDuTemps save(@RequestBody EmploiDuTemps emploiDuTemps) {
        return service.save(emploiDuTemps);
    }

    @GetMapping
    public Page<EmploiDuTemps> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public EmploiDuTemps getById(@PathVariable Long id) {
        return service.finById(id);
    }

    @PutMapping("/{id}")
    public EmploiDuTemps update(
            @PathVariable Long id,
            @RequestBody EmploiDuTemps emploiDuTemps
    ) {
        return service.update(id, emploiDuTemps);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/complet/{id}")
    public Map<String, Object> getComplet(@PathVariable Long id) {
        return service.getNoteComplet(id);
    }

}
