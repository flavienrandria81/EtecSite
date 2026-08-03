package com.visio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalleVisioRequest {

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    private String description;

    @NotNull(message = "L'enseignant est obligatoire")
    private Long enseignantId;

    private Long matiereId;

    private Long coursId;

    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    @Builder.Default
    private List<Long> etudiantIds = new ArrayList<>();
}
