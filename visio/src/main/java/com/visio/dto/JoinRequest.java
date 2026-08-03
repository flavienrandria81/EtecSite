package com.visio.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinRequest {

    private String nom;
    private String role;
}
