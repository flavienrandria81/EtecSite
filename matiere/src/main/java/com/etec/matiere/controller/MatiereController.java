package com.etec.matiere.controller;

import com.etec.matiere.entity.Matiere;
import com.etec.matiere.service.MatiereService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matieres")
@RequiredArgsConstructor
public class MatiereController {

    private final MatiereService matiereService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Matiere create(@RequestBody Matiere matiere) {
        return matiereService.save(matiere);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Page<Matiere> getAll(Pageable pageable) {
        return matiereService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Matiere getById(@PathVariable Long id) {
        return matiereService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Matiere update(@PathVariable Long id,
                        @RequestBody Matiere matiere) {
        return matiereService.update(id, matiere);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public void delete(@PathVariable Long id) {
        matiereService.delete(id);
    }
}
