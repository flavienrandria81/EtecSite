package com.example.Etudiant.demo.controller;

import com.example.Etudiant.demo.entity.Etudiant;
import com.example.Etudiant.demo.entity.TypeFormation;
import com.example.Etudiant.demo.service.EtudiantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/etudiants")
@RequiredArgsConstructor
public class EtudiantController {

    private final EtudiantService etudiantService;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Etudiant create(@RequestParam String matricule,
                           @RequestParam String cin,
                           @RequestParam String adresse,
                           @RequestParam String phone,
                           @RequestParam TypeFormation typeFormation,
                           @RequestParam Long filiereId,
                           @RequestParam Long niveauId,
                           @RequestParam Long domaineId,
                           @RequestParam("photo") MultipartFile photo,
                           @RequestParam("releve") MultipartFile releve,
                           @RequestParam("diplome") MultipartFile diplome,
                           HttpServletRequest request) {

        return etudiantService.createEtudiant(
                matricule,
                cin,
                adresse,
                phone,
                typeFormation,
                filiereId,
                niveauId,
                domaineId,
                photo,
                releve,
                diplome,
                request);
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
}