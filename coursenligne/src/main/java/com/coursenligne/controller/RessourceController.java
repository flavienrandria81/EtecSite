package com.coursenligne.controller;


import com.coursenligne.entity.Ressource;
import com.coursenligne.service.RessourceService;


import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/api/ressources")
@RequiredArgsConstructor
public class RessourceController {



    private final RessourceService ressourceService;






    // Créer une ressource
    @PostMapping
    public ResponseEntity<Ressource> creerRessource(
            @RequestBody Ressource ressource
    ){


        Ressource nouvelle =
                ressourceService.creerRessource(
                        ressource
                );


        return new ResponseEntity<>(
                nouvelle,
                HttpStatus.CREATED
        );

    }









    // Modifier
    @PutMapping("/{id}")
    public ResponseEntity<Ressource> modifier(
            @PathVariable Long id,
            @RequestBody Ressource ressource
    ){


        return ResponseEntity.ok(
                ressourceService.modifierRessource(
                        id,
                        ressource
                )
        );

    }









    // Toutes les ressources
    @GetMapping
    public ResponseEntity<List<Ressource>> getAll(){


        return ResponseEntity.ok(
                ressourceService.getAll()
        );

    }









    // Une ressource par ID
    @GetMapping("/{id}")
    public ResponseEntity<Ressource> getById(
            @PathVariable Long id
    ){


        return ResponseEntity.ok(
                ressourceService.getById(id)
        );

    }









    // Ressources d'une leçon
    @GetMapping("/lecon/{leçonId}")
    public ResponseEntity<List<Ressource>> getByLecon(
            @PathVariable Long leçonId
    ){


        return ResponseEntity.ok(
                ressourceService.getByLeconId(
                        leçonId
                )
        );

    }









    // Supprimer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id
    ){


        ressourceService.supprimerRessource(id);


        return ResponseEntity.noContent()
                .build();

    }

}