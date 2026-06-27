package com.etec.matiere.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SemestreDto {
    private Long id;
    private Long anneesUnivId;
    private String nom;
    private Integer numero;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private  boolean actif;

}
