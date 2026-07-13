package com.coursenligne.controller;


import com.coursenligne.entity.Chapitre;
import com.coursenligne.service.ChapitreService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/chapitres")
@RequiredArgsConstructor
public class ChapitreController {



    private final ChapitreService chapitreService;



    @PostMapping
    public ResponseEntity<Chapitre> creer(
            @RequestBody Chapitre chapitre
    ){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        chapitreService.creerChapitre(chapitre)
                );
    }




    @GetMapping
    public ResponseEntity<List<Chapitre>> getAll(){

        return ResponseEntity.ok(
                chapitreService.getAll()
        );
    }




    @GetMapping("/{id}")
    public ResponseEntity<Chapitre> getById(
            @PathVariable Long id
    ){

        return ResponseEntity.ok(
                chapitreService.getById(id)
        );
    }





    @GetMapping("/cours/{coursId}")
    public ResponseEntity<List<Chapitre>> getByCours(
            @PathVariable Long coursId
    ){

        return ResponseEntity.ok(
                chapitreService.getByCoursId(coursId)
        );
    }






    @PutMapping("/{id}")
    public ResponseEntity<Chapitre> modifier(
            @PathVariable Long id,
            @RequestBody Chapitre chapitre
    ){

        return ResponseEntity.ok(
                chapitreService.modifierChapitre(
                        id,
                        chapitre
                )
        );
    }





    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id
    ){

        chapitreService.supprimerChapitre(id);

        return ResponseEntity.noContent()
                .build();
    }

}