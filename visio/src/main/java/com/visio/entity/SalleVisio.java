package com.visio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "salle_visio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalleVisio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Column(length = 3000)
    private String description;

    private Long enseignantId;

    private Long matiereId;

    private Long coursId;

    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StatutSalle statut = StatutSalle.PLANIFIEE;

    @Column(unique = true, length = 20)
    private String codeAcces;

    @Column(length = 500)
    private String lienConnexion;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
