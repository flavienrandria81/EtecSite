package com.etec.matiere.controller;

import com.etec.matiere.entity.Matiere;
import com.etec.matiere.service.MatiereService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matieres")
@RequiredArgsConstructor
public class MatiereController {

    private final MatiereService matiereService;

    @PostMapping("/save")
    public Matiere create(@RequestBody Matiere matiere) {
        return matiereService.save(matiere);
    }

    @GetMapping
    public Page<Matiere> getAll(Pageable pageable) {
        return matiereService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Matiere getById(@PathVariable Long id) {
        return matiereService.findById(id);
    }

    @PutMapping("/{id}")
    public Matiere update(@PathVariable Long id,
                        @RequestBody Matiere matiere) {
        return matiereService.update(id, matiere);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        matiereService.delete(id);
    }
}
