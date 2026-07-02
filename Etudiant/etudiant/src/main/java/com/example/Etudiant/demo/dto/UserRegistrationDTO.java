package com.example.Etudiant.demo.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {

    private String username;
    private String email;
    private String password;
    private String role;
}
