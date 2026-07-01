package com.example.Encadreur.demo.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Encadreur")
@Data
public class Encadreur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_encadreur;
    private String nom;
    private String prenom;
    private String grade;
    private String description;


    public Encadreur(){

    }

}
