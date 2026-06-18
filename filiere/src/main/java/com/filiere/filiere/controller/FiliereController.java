package com.filiere.filiere.controller;

import com.filiere.filiere.entity.Filiere;
import com.filiere.filiere.service.FiliereService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/filieres")
public class FiliereController {

    private final FiliereService service;

    @PostMapping("/save")
    public Filiere save(@RequestBody Filiere filiere) {
        return service.save(filiere);
    }

    @GetMapping
    public Page<Filiere> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Filiere getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Filiere update(@PathVariable Long id,
                          @RequestBody Filiere filiere) {
        return service.update(id, filiere);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
