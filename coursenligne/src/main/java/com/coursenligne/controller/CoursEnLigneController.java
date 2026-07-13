package com.coursenligne.controller;

import com.coursenligne.dto.CoursResponse;
import com.coursenligne.entity.CoursEnLigne;
import com.coursenligne.service.CoursEnLigneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cours")
@RequiredArgsConstructor
public class CoursEnLigneController {

    private final CoursEnLigneService coursService;

    @PostMapping
    public ResponseEntity<CoursEnLigne> creerCours(
            @RequestBody CoursEnLigne cours) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(coursService.creerCours(cours));
    }

    @GetMapping
    public ResponseEntity<Page<CoursEnLigne>> getAll(Pageable pageable) {
        return ResponseEntity.ok(
                coursService.getAll(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoursEnLigne> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                coursService.getById(id)
        );
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<CoursResponse> getCoursDetails(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                coursService.getCoursById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        coursService.delete(id);
        return ResponseEntity.noContent().build();
    }

}