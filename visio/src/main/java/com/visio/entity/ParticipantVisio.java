package com.visio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "participant_visio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantVisio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long salleVisioId;

    private Long utilisateurId;

    private String nom;

    @Enumerated(EnumType.STRING)
    private RoleParticipant role;

    private String sessionId;

    @Builder.Default
    private boolean actif = true;

    @Builder.Default
    private LocalDateTime heureConnexion = LocalDateTime.now();

    private LocalDateTime heureDeconnexion;
}
