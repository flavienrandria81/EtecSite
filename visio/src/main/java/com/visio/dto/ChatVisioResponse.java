package com.visio.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatVisioResponse {

    private Long id;
    private Long salleVisioId;
    private Long utilisateurId;
    private String nom;
    private String message;
    private LocalDateTime dateEnvoi;
}
