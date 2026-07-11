package com.example.Etudiant.demo.controller;


import com.example.Etudiant.demo.dto.EtudiantRegistrationDTO;
import com.example.Etudiant.demo.entity.Etudiant;
import com.example.Etudiant.demo.entity.TypeFormation;
import com.example.Etudiant.demo.service.EtudiantService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Map;


@RestController
@RequestMapping("/etudiants")
@RequiredArgsConstructor
public class EtudiantController {


    private final EtudiantService etudiantService;



    // =====================================================
    // INSCRIPTION ETUDIANT
    // =====================================================

    @PostMapping(
            value = "/registration",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Etudiant> register(


            @RequestPart("data")
            EtudiantRegistrationDTO dto,


            @RequestPart("photo")
            MultipartFile photo,


            @RequestPart("releve")
            MultipartFile releve,


            @RequestPart("diplome")
            MultipartFile diplome


    ){

        Etudiant etudiant =
                etudiantService.registerEtudiantComplete(
                        dto,
                        photo,
                        releve,
                        diplome
                );


        return ResponseEntity.ok(etudiant);

    }





    // =====================================================
    // ADMIN : VALIDER ETUDIANT
    // Génère QR Code + Email
    // =====================================================


    @PutMapping("/validation/{id}")
    public ResponseEntity<Etudiant> valider(


            @PathVariable Long id

    ){


        Etudiant etudiant =
                etudiantService.validerEtudiant(id);



        return ResponseEntity.ok(etudiant);

    }





    // =====================================================
    // ADMIN : REFUSER ETUDIANT
    // =====================================================


    @PutMapping("/refus/{id}")
    public ResponseEntity<Etudiant> refuser(


            @PathVariable Long id


    ){


        Etudiant etudiant =
                etudiantService.refuserEtudiant(id);



        return ResponseEntity.ok(etudiant);

    }





    // =====================================================
    // LISTE ETUDIANTS
    // =====================================================


    @GetMapping
    public ResponseEntity<Page<Etudiant>> getAll(


            Pageable pageable


    ){


        return ResponseEntity.ok(
                etudiantService.getAllEtudiants(pageable)
        );

    }





    // =====================================================
    // RECHERCHE PAR ID
    // =====================================================


    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> getById(


            @PathVariable Long id


    ){


        return ResponseEntity.ok(
                etudiantService.getEtudiantById(id)
        );

    }





    // =====================================================
    // RECHERCHE PAR MATRICULE
    // =====================================================


    @GetMapping("/matricule/{matricule}")
    public ResponseEntity<Etudiant> getByMatricule(


            @PathVariable String matricule


    ){


        return ResponseEntity.ok(
                etudiantService.getByMatricule(matricule)
        );

    }





    // =====================================================
    // DETAILS COMPLETS
    // User + Filiere + Niveau + Domaine
    // =====================================================


    @GetMapping("/complet/{id}")
    public ResponseEntity<Map<String,Object>> getComplet(


            @PathVariable Long id


    ){


        return ResponseEntity.ok(
                etudiantService.getEtudiantComplet(id)
        );

    }





    // =====================================================
    // QR CODE IMAGE
    // =====================================================


    @GetMapping("/{id}/qr")
    public ResponseEntity<byte[]> getQrCode(


            @PathVariable Long id


    ){


        Etudiant etudiant =
                etudiantService.getEtudiantById(id);



        if(etudiant.getQrCode() == null){

            return ResponseEntity
                    .notFound()
                    .build();

        }



        return ResponseEntity.ok()
                .contentType(
                        MediaType.IMAGE_PNG
                )
                .body(
                        etudiant.getQrCode()
                );

    }





    // =====================================================
    // MODIFICATION ETUDIANT
    // =====================================================


    @PutMapping(
            value="/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Etudiant> update(


            @PathVariable Long id,


            @RequestParam String matricule,


            @RequestParam String cin,


            @RequestParam String adresse,


            @RequestParam String phone,


            @RequestParam(required = false)
            TypeFormation typeFormation,


            @RequestParam(
                    value="photo",
                    required=false
            )
            MultipartFile photo,


            @RequestParam(
                    value="releve",
                    required=false
            )
            MultipartFile releve,


            @RequestParam(
                    value="diplome",
                    required=false
            )
            MultipartFile diplome



    ){


        return ResponseEntity.ok(

                etudiantService.updateEtudiant(
                        id,
                        matricule,
                        cin,
                        adresse,
                        phone,
                        typeFormation,
                        photo,
                        releve,
                        diplome
                )

        );

    }





    // =====================================================
    // SUPPRESSION
    // =====================================================


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(


            @PathVariable Long id


    ){


        etudiantService.deleteEtudiant(id);


        return ResponseEntity.ok(
                "Etudiant supprimé"
        );

    }



}