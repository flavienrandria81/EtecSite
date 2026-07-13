package com.coursenligne.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "ressources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ressource {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nom;


    private String url;


    private Double taille;


    private LocalDate dateAjout;


    @Enumerated(EnumType.STRING)
    private TypeRessource type;


    // Une ressource appartient à une leçon

    @ManyToOne
    @JoinColumn(name = "leçon_id")
    private Lecon lecon;

}