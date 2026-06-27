package com.abscence.presence.dto;

import lombok.Data;

@Data
public class EtudiantDto {

    private Long id;
    private Long userId;
    private Long filiereId;
    private Long niveauId;
    private Long domaineId;
    private TypeFormation typeFormation;

    private String matricule;
    private String cin;
    private String adresse;
    private String phone;
    private String photo;
    private String releve;
    private String diplome;
}
