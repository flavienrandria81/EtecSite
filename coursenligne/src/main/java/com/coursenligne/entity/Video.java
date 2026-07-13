package com.coursenligne.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "videos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String urlVideo;

    private Integer duree;

    @Enumerated(EnumType.STRING)
    private TypeVideo type;

    @ManyToOne
    @JoinColumn(name="leçon_id")
    private Lecon lecon;
}
