package com.example.Etudiant.demo.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String username;

    private String nom;

    private String prenom;

    private String email;

    private String role;
}