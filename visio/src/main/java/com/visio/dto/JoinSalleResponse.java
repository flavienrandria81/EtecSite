package com.visio.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinSalleResponse {

    private boolean autorise;
    private String message;
    private SalleVisioResponse salle;
    private ParticipantVisioResponse participant;
    private String nom;
    private String role;
}
