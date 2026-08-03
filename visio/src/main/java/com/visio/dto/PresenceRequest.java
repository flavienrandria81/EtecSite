package com.visio.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresenceRequest {

    private Long etudiantId;
    private Long userId;
    private Long emploiDuTempsId;
    private LocalDate datePresence;
    private LocalTime heurePresence;
    private String statut;
    private String remarque;
}
