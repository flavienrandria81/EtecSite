package com.coursenligne.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cours")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursEnLigne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Column(length = 3000)
    private String description;

    private Long enseignantId;

    private Long matiereId;

    private Long filiereId;

    private Long niveauId;

    private Long domaineId;

    private Boolean actif = true;

    @OneToMany(
            mappedBy = "cours",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Chapitre> chapitres;
}