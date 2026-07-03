package com.coursenligne.controller;

import com.coursenligne.dto.CoursResponse;
import com.coursenligne.entity.CoursEnLigne;
import com.coursenligne.service.CoursEnLigneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cours")
@RequiredArgsConstructor
public class CoursController {

    private final CoursEnLigneService service;

    @PostMapping
    public CoursEnLigne create(@RequestBody CoursEnLigne cours) {
        return service.creerCours(cours);
    }

    @GetMapping
    public Page<CoursEnLigne> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public CoursEnLigne getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/details/{id}")
    public CoursResponse getCoursDetails(@PathVariable Long id) {
        return service.getCoursById(id);
    }
}
