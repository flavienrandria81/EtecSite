package com.coursenligne.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "chapitres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapitre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Column(length = 3000)
    private String description;

    private Integer ordre;

    @ManyToOne
    @JoinColumn(name="cours_id")
    private CoursEnLigne cours;


    @OneToMany(
            mappedBy="chapitre",
            cascade=CascadeType.ALL
    )
    private List<Lecon> lecons;

}
