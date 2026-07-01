package com.etudiant.note.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long etudiantId;
    private Long enseignantId;
    private Long semestreId;
    private Long anneesUnivId;
    private Long matiereId;
    private Long userId;
    private Long filiereId;
    private Long niveauId;
    private Long domaineId;
    private double valeur;
    private String mention;

}
