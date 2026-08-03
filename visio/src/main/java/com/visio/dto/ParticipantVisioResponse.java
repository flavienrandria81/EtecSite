package com.visio.dto;

import com.visio.entity.RoleParticipant;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantVisioResponse {

    private Long id;
    private Long salleVisioId;
    private Long utilisateurId;
    private String nom;
    private RoleParticipant role;
    private String sessionId;
    private boolean actif;
    private LocalDateTime heureConnexion;
    private LocalDateTime heureDeconnexion;
}
