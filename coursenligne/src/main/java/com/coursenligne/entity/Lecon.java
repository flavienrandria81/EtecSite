package com.coursenligne.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lecon {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String titre;


    @Column(columnDefinition="TEXT")
    private String contenu;


    private Integer duree;


    @ManyToOne
    @JoinColumn(name="chapitre_id")
    private Chapitre chapitre;



    @OneToMany(
            mappedBy="lecon",
            cascade=CascadeType.ALL
    )
    private List<Video> videos;
}
