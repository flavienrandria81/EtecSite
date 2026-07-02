package com.example.Etudiant.demo.controller;

import com.example.Etudiant.demo.dto.EtudiantRegistrationDTO;
import com.example.Etudiant.demo.entity.Etudiant;
import com.example.Etudiant.demo.entity.TypeFormation;
import com.example.Etudiant.demo.service.EtudiantService;
import jakarta.servlet.http.HttpServletRequest;
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


    @PostMapping(value = "/registration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Etudiant> create(
            @RequestPart("data") EtudiantRegistrationDTO dto,
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("releve") MultipartFile releve,
            @RequestPart("diplome") MultipartFile diplome,
            HttpServletRequest request
    ) {

        Etudiant saved = etudiantService.registerEtudiantComplete(
                dto,
                photo,
                releve,
                diplome,
                request
        );

        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public Page<Etudiant> getAll(Pageable pageable) {
        return etudiantService.getAllEtudiants(pageable);
    }

    @GetMapping("/id/{id}")
    public Etudiant getById(@PathVariable Long id) {
        return etudiantService.getEtudiantById(id);
    }

    @GetMapping("/matricule/{matricule}")
    public Etudiant getByMatricule(@PathVariable String matricule) {
        return etudiantService.getByMatricule(matricule);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Etudiant update(@PathVariable Long id,
                           @RequestParam String matricule,
                           @RequestParam String cin,
                           @RequestParam String adresse,
                           @RequestParam String phone,
                           @RequestParam(required = false) TypeFormation typeFormation,
                           @RequestParam(value = "photo", required = false) MultipartFile photo,
                           @RequestParam(value = "releve", required = false) MultipartFile releve,
                           @RequestParam(value = "diplome", required = false) MultipartFile diplome
                           ) {
        return etudiantService.updateEtudiant(id, matricule, cin, adresse, phone, typeFormation, photo, releve, diplome);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
    }

    @GetMapping("/complet/{id}")
    public Map<String, Object> getComplet(@PathVariable Long id) {
        return etudiantService.getEtudiantComplet(id);
    }

    @GetMapping("/{id}/qr")
    public ResponseEntity<byte[]> getQrCode(@PathVariable Long id) {

        Etudiant etudiant = etudiantService.getEtudiantById(id);

        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(etudiant.getQrCode());
    }
}