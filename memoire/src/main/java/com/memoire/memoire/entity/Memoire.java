package com.memoire.memoire.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Memoire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long etudiantId;
    private Long userId;
    private Long noteId;
    private Long ensignantId;
    private Long filiereId;
    private Long niveauId;
    private Long domaineId;
    private String theme;
    private String description;
    private String livre;

    @Enumerated(EnumType.STRING)
    private StatutMemoire statut;
}
