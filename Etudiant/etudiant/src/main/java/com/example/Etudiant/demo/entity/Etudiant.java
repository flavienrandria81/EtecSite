package com.example.Etudiant.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "etudiants")
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long filiereId;
    private Long niveauId;
    private Long domaineId;
    @Enumerated(EnumType.STRING)
    private TypeFormation typeFormation;

    @Enumerated(EnumType.STRING)
    private StatutEtudiant statut;

    private String nom;
    private String prenom;
    @Column(unique = true)
    private String matricule;
    private String cin;
    private String adresse;
    private String phone;
    private String photo;
    private String releve;
    private String diplome;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] qrCode;
}