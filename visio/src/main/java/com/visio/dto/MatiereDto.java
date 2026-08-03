package com.visio.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatiereDto {

    private Long id;
    private Long semestreId;
    private String nom;
    private Integer coefficient;
}
