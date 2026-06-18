package com.niveau.niveau.controller;

import com.niveau.niveau.entity.Niveau;
import com.niveau.niveau.service.NiveauService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/niveau")
public class NiveauController {

    private final NiveauService service;

    @PostMapping("/save")
    public Niveau save(@RequestBody Niveau niveau) {
        return service.save(niveau);
    }

    @GetMapping
    public Page<Niveau> getAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Niveau getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Niveau update(@PathVariable Long id,
                         @RequestBody Niveau niveau) {
        return service.update(id, niveau);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
