package com.example.Etudiant.demo.client;

import com.example.Etudiant.demo.config.FeignConfig;
import com.example.Etudiant.demo.dto.UserDto;
import com.example.Etudiant.demo.dto.UserRegistrationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "UTILISATEUR",  configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("/api/auth/users/{id}")
    UserDto getUserById(@PathVariable Long id);

    @PostMapping("/api/auth/registration")
    UserDto register(@RequestBody UserRegistrationDTO dto);

}