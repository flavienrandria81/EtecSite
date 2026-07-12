package com.filiere.filiere.controller;

import com.filiere.filiere.entity.Filiere;
import com.filiere.filiere.service.FiliereService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/filieres")
public class FiliereController {

    private final FiliereService service;


    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Filiere save(@RequestBody Filiere filiere) {
        return service.save(filiere);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Page<Filiere> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Filiere getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Filiere update(@PathVariable Long id,
                          @RequestBody Filiere filiere) {
        return service.update(id, filiere);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
