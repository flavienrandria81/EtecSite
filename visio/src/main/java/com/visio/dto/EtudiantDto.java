package com.visio.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtudiantDto {

    private Long id;
    private Long userId;
    private Long filiereId;
    private Long niveauId;
    private Long domaineId;
    private String nom;
    private String prenom;
    private String matricule;
    private String statut;
    private String typeFormation;
}
