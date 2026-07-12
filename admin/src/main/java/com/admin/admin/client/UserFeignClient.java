package com.admin.admin.client;

import com.admin.admin.dto.UserRegistrationDTO;
import com.admin.admin.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "UTILISATEUR")
public interface UserFeignClient {


    /**
     * Création d'un ADMIN
     * Appelle User Service
     */
    @PostMapping("/api/auth/register-admin")
    ResponseEntity<UserResponseDTO> registerAdmin(
            @RequestBody UserRegistrationDTO adminUser
    );



    /**
     * Création du SUPER_ADMIN
     * Uniquement une fois
     */
    @PostMapping("/api/auth/register-super-admin")
    ResponseEntity<UserResponseDTO> registerSuperAdmin(
            @RequestBody UserRegistrationDTO user
    );



    /**
     * Récupérer un utilisateur depuis User Service
     */
    @GetMapping("/api/auth/users/{id}")
    ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable("id") Long id
    );



    /**
     * Supprimer utilisateur
     */
    @DeleteMapping("/api/auth/users/{id}")
    ResponseEntity<?> deleteUser(
            @PathVariable("id") Long id
    );

}