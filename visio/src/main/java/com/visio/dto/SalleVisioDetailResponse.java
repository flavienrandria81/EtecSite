package com.visio.dto;

import com.visio.entity.StatutSalle;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalleVisioDetailResponse {

    private Long id;
    private String titre;
    private String description;
    private Long enseignantId;
    private Long matiereId;
    private Long coursId;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private StatutSalle statut;
    private String codeAcces;
    private String lienConnexion;

    private EnseignantDto enseignant;
    private MatiereDto matiere;
    private CoursDto cours;
}
