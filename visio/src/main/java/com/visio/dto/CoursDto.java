package com.visio.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoursDto {

    private Long id;
    private String titre;
    private String description;
    private Long enseignantId;
    private Long matiereId;
    private Long filiereId;
    private Long niveauId;
    private Long domaineId;
}
