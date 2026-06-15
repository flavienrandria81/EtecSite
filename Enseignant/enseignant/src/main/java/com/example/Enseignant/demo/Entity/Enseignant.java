package com.example.Enseignant.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enseignant")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enseignant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_enseignant;
    private String nom;
    private String prenom;
    private String cours;
    private String photo;
    private String adresse;
    private String mail;
    private String cin;
    private String statue;
    private String id_etudiant;
    private String id_filier;

    /*public Enseignant(){

    }*/

}
