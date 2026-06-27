package com.sem.semestre.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "semestres")
public class Semestre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long anneesUnivId;
    private Long matiereId;
    private String nom;
    private Integer numero;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private  boolean actif;
}
