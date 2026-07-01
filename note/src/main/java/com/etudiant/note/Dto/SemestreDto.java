package com.etudiant.note.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SemestreDto {
    private Long id;
    private String nom;
    private Integer numero;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private  boolean actif;
}
