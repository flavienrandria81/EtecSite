package com.encadrement.demo.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="encadrement")
@Data
public class Encadrement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_encadrement;
    /*private String id_encadreur;
    private String id_filier;
    private String id_etudiant;*/
    private String theme;
    private String description;

    public Encadrement(){

    }

}
