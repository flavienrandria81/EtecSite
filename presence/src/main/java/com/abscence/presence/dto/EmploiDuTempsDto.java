package com.abscence.presence.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EmploiDuTempsDto {

    private Long id;
    private Long filiereId;
    private Long matiereId;
    private Long enseignantId;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String jours;
    private String salle;

}
