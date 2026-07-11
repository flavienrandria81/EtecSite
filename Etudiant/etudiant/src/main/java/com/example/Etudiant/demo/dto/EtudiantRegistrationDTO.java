package com.example.Etudiant.demo.dto;

import com.example.Etudiant.demo.entity.TypeFormation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EtudiantRegistrationDTO {


    // User
    private String username;

    private String nom;

    private String prenom;

    private String email;

    private String password;



    // Etudiant
    private String matricule;

    private String cin;

    private String telephone;

    private String adresse;


    private TypeFormation typeFormation;


    private Long filiereId;

    private Long niveauId;

    private Long domaineId;

}