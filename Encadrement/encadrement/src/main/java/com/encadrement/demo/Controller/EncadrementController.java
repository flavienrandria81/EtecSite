package com.encadrement.demo.Controller;

import com.encadrement.demo.Entity.Encadrement;
import com.encadrement.demo.Service.EncadrementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/encadrements")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EncadrementController {

    private final EncadrementService service;

    @PostMapping
    public Encadrement save(@RequestBody Encadrement encadrement) {
        return service.save(encadrement);
    }

    @PutMapping("/{id}")
    public Encadrement update(@PathVariable Long id,
                              @RequestBody Encadrement encadrement) {
        return service.update(id, encadrement);
    }

    @GetMapping("/{id}")
    public Encadrement findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public Page<Encadrement> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}