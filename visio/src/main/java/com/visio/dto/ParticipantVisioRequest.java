package com.visio.dto;

import com.visio.entity.RoleParticipant;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantVisioRequest {

    @NotNull(message = "L'identifiant utilisateur est obligatoire")
    private Long utilisateurId;

    @NotNull(message = "Le nom est obligatoire")
    private String nom;

    @NotNull(message = "Le rôle est obligatoire")
    private RoleParticipant role;
}
