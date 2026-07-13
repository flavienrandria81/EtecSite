package com.coursenligne.controller;


import com.coursenligne.entity.Lecon;

import com.coursenligne.service.LeconService;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/lecons")
@RequiredArgsConstructor
public class LeconController {



    private final LeconService leconService;





    @PostMapping
    public ResponseEntity<Lecon> creer(
            @RequestBody Lecon lecon
    ){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        leconService.creerLecon(lecon)
                );
    }



    @GetMapping
    public ResponseEntity<Page<Lecon>> getAll(Pageable pageable){

        return ResponseEntity.ok(
                leconService.getAll(pageable)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<Lecon> getById(
            @PathVariable Long id
    ){

        return ResponseEntity.ok(
                leconService.getById(id)
        );
    }




    @GetMapping("/chapitre/{chapitreId}")
    public ResponseEntity<List<Lecon>> getByChapitre(
            @PathVariable Long chapitreId
    ){

        return ResponseEntity.ok(
                leconService.getByChapitreId(
                        chapitreId
                )
        );
    }



    @PutMapping("/{id}")
    public ResponseEntity<Lecon> modifier(
            @PathVariable Long id,
            @RequestBody Lecon lecon
    ){

        return ResponseEntity.ok(
                leconService.modifierLecon(
                        id,
                        lecon
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id
    ){

        leconService.supprimerLecon(id);


        return ResponseEntity.noContent()
                .build();
    }

}