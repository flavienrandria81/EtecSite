package com.etudiant.note.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AnneesUnivDto {
    private Long id;

    private String libelle;
    private LocalDate debut;
    private LocalDate fin;
    private boolean actif;
}
