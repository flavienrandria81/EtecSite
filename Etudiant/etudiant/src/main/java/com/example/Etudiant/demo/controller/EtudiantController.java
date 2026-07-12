package com.example.Etudiant.demo.controller;


import com.example.Etudiant.demo.dto.EtudiantRegistrationDTO;
import com.example.Etudiant.demo.entity.Etudiant;
import com.example.Etudiant.demo.entity.TypeFormation;
import com.example.Etudiant.demo.service.EtudiantService;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;


import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/etudiants")
@RequiredArgsConstructor
public class EtudiantController {


    private final EtudiantService etudiantService;


    @PostMapping(value = "/registration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(

            @RequestPart("data") String data,
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("releve") MultipartFile releve,
            @RequestPart("diplome") MultipartFile diplome

    ) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        EtudiantRegistrationDTO dto =
                mapper.readValue(
                        data,
                        EtudiantRegistrationDTO.class
                );

        Etudiant etudiant = etudiantService.registerEtudiantComplete(
                        dto,
                        photo,
                        releve,
                        diplome
               
                );

        return ResponseEntity.ok(etudiant);
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<Page<Etudiant>> findAll(Pageable pageable){

        return ResponseEntity.ok(
                etudiantService.getAllEtudiants(pageable)
        );

    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<Etudiant> findById(@PathVariable Long id){

        return ResponseEntity.ok(
                etudiantService.getEtudiantById(id)
        );

    }


    @PutMapping("/{id}/valider")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Etudiant> valider(@PathVariable Long id){

        return ResponseEntity.ok(
                etudiantService.validerEtudiant(id)
        );

    }


    @PutMapping("/{id}/refuser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Etudiant> refuser(@PathVariable Long id){
        return ResponseEntity.ok(
                etudiantService.refuserEtudiant(id)
        );

    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Etudiant> update(

            @PathVariable Long id,
            @RequestParam String matricule,
            @RequestParam String cin,
            @RequestParam String adresse,
            @RequestParam String phone,
            @RequestParam(required = false)
            TypeFormation typeFormation, @RequestPart(required = false) MultipartFile photo,
            @RequestPart(required = false) MultipartFile releve,
            @RequestPart(required = false) MultipartFile diplome

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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> delete(
            @PathVariable Long id
    ){
        etudiantService.deleteEtudiant(id);

        return ResponseEntity.ok(
                "Etudiant supprimé"
        );

    }

    @GetMapping("/{id}/qr")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN','ETUDIANT')")
    public ResponseEntity<ByteArrayResource> getQr(
            @PathVariable Long id
    ){

        Etudiant etudiant = etudiantService.getEtudiantById(id);

        ByteArrayResource resource =
                new ByteArrayResource(
                        etudiant.getQrCode()
                );


        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=QR.png"
                )

                .contentType(
                        MediaType.IMAGE_PNG
                )

                .body(resource);

    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ETUDIANT')")
    public ResponseEntity<Etudiant> myProfile(
            @RequestAttribute("userId")
            Long userId
    ){

        return ResponseEntity.ok(

                etudiantService.getByUserId(userId)

        );

    }



}