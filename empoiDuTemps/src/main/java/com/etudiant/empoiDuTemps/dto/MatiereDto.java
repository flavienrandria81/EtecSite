package com.etudiant.empoiDuTemps.dto;

import lombok.Data;

@Data
public class MatiereDto {
    private Long id;
    private Long semestreId;
    private String nom;
    private Integer coefficient;
}
