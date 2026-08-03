package com.visio.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatVisioRequest {

    @NotNull(message = "L'identifiant utilisateur est obligatoire")
    private Long utilisateurId;

    private String nom;

    @NotNull(message = "Le message est obligatoire")
    private String message;
}
