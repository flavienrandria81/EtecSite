package com.enligne.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "enligne")
@Data
public class Enligne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_video;
    private String description;

    @Column(name = "etat")
    private String condition;

    private String id_cours;
    private String id_filiers;
    private String id_etudiants;
    private String id_formations;

    public Enligne(){

    }

}
