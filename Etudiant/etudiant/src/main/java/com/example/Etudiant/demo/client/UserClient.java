package com.example.Etudiant.demo.client;

import com.example.Etudiant.demo.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UTILISATEUR")
public interface UserClient {

    @GetMapping("/users/{id}")
    UserDto getUserById(@PathVariable Long id);
}