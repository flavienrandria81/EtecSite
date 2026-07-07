package com.coursenligne.controller;


import com.coursenligne.entity.Leçon;
import com.coursenligne.service.LeçonService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/lecons")
@RequiredArgsConstructor
public class LeçonController {



    private final LeçonService leçonService;




    // ============================
    // Créer une leçon
    // ============================
    @PostMapping
    public ResponseEntity<Leçon> creerLecon(
            @RequestBody Leçon leçon
    ){

        Leçon nouvelleLecon =
                leçonService.creerLecon(leçon);


        return new ResponseEntity<>(
                nouvelleLecon,
                HttpStatus.CREATED
        );
    }







    // ============================
    // Modifier une leçon
    // ============================
    @PutMapping("/{id}")
    public ResponseEntity<Leçon> modifierLecon(
            @PathVariable Long id,
            @RequestBody Leçon leçon
    ){

        Leçon modifier =
                leçonService.modifierLecon(
                        id,
                        leçon
                );


        return ResponseEntity.ok(modifier);
    }








    // ============================
    // Récupérer toutes les leçons
    // ============================
    @GetMapping
    public ResponseEntity<List<Leçon>> getAll(){

        return ResponseEntity.ok(
                leçonService.getAll()
        );
    }








    // ============================
    // Récupérer une leçon par ID
    // ============================
    @GetMapping("/{id}")
    public ResponseEntity<Leçon> getById(
            @PathVariable Long id
    ){

        return ResponseEntity.ok(
                leçonService.getById(id)
        );
    }








    // ============================
    // Récupérer les leçons d'un chapitre
    // ============================
    @GetMapping("/chapitre/{chapitreId}")
    public ResponseEntity<List<Leçon>> getByChapitreId(
            @PathVariable Long chapitreId
    ){

        return ResponseEntity.ok(
                leçonService.getByChapitreId(chapitreId)
        );
    }








    // ============================
    // Supprimer une leçon
    // ============================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id
    ){

        leçonService.supprimerLecon(id);


        return ResponseEntity.noContent()
                .build();
    }

}