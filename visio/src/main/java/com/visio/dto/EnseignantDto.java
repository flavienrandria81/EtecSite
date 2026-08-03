package com.visio.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnseignantDto {

    private Long id;
    private Long userId;
    private Long matiereId;
    private Long filiereId;
    private String matricule;
    private String specialite;
    private String phone;
    private String email;
}
