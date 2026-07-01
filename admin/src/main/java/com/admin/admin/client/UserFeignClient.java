package com.admin.admin.client;

import com.admin.admin.dto.UserRegistrationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "UTILISATEUR") // Nom exact du microservice utilisateur dans Eureka/Consul
public interface UserFeignClient {

    // On cible précisément l'endpoint protégé du User Service
    @PostMapping("/api/auth/register_admin")
    ResponseEntity<?> registerAdmin(@RequestBody UserRegistrationDTO adminUser);

    @GetMapping("/api/auth/users/{id}")
    ResponseEntity<UserRegistrationDTO> getUserById(@PathVariable("id") Long id);

    // Pour supprimer les identifiants en base
    @DeleteMapping("/api/auth/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") Long id);
}