package com.etudiant.empoiDuTemps.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "emploiDuTemps")
public class EmploiDuTemps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long filiereId;
    private Long matiereId;
    private Long enseignantId;
    private Long niveauId;
    private Long semestreId;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String jours;
    private String salle;


}
