package com.admin.admin.dto;

import lombok.Data;

@Data // Génère automatiquement les getters, setters et constructeurs grâce à Lombok
public class UserRegistrationDTO {
    private String username;
    private String email;
    private String password;
    private String role; // "SUPER_ADMIN", "ADMIN", ou "PEDAGOGIQUE_ADMIN"
}