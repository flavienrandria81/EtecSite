package com.dom.domaine.controller;

import com.dom.domaine.entity.Domaine;
import com.dom.domaine.service.DomaineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/domaines")
public class DomaineController {

    private final DomaineService service;

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Domaine save(@RequestBody Domaine domaine) {
        return service.save(domaine);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Page<Domaine> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Domaine getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Domaine update(@PathVariable Long id,
                          @RequestBody Domaine domaine) {
        return service.update(id, domaine);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
