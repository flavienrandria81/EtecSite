package com.dom.domaine.controller;

import com.dom.domaine.entity.Domaine;
import com.dom.domaine.service.DomaineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/domaines")
public class DomaineController {

    private final DomaineService service;

    @PostMapping("/save")
    public Domaine save(@RequestBody Domaine domaine) {
        return service.save(domaine);
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
    public Domaine update(@PathVariable Long id,
                          @RequestBody Domaine domaine) {
        return service.update(id, domaine);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
