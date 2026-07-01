package com.admin.admin.dto;

import lombok.Data;

@Data
public class AdminDto {
    private Long userId;
    private String username;
    private String email;
    private String role;
    private String nom;
    private String prenom;
    private String departement;
}
