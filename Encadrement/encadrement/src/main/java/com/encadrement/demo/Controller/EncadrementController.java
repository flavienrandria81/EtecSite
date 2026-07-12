package com.encadrement.demo.Controller;

import com.encadrement.demo.Entity.Encadrement;
import com.encadrement.demo.Service.EncadrementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/encadrements")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EncadrementController {

    private final EncadrementService service;

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Encadrement save(@RequestBody Encadrement encadrement) {
        return service.save(encadrement);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Encadrement update(@PathVariable Long id,
                              @RequestBody Encadrement encadrement) {
        return service.update(id, encadrement);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Encadrement findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public Page<Encadrement> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}