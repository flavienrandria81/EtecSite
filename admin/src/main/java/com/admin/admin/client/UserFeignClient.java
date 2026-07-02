package com.admin.admin.client;

import com.admin.admin.dto.UserRegistrationDTO;
import com.admin.admin.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "UTILISATEUR")
public interface UserFeignClient {

    @PostMapping("/api/auth/register-admin")
    ResponseEntity<UserResponseDTO> registerAdmin(
            @RequestBody UserRegistrationDTO adminUser
    );

    @GetMapping("/api/auth/users/{id}")
    ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable Long id
    );

    @DeleteMapping("/api/auth/users/{id}")
    ResponseEntity<?> deleteUser(
            @PathVariable Long id
    );

}