package com.example.Etudiant.demo.client;

import com.example.Etudiant.demo.dto.FiliereDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "FILIERE")
public interface FiliereClient {

    @GetMapping("/filieres/{id}")
    FiliereDto getFiliere(@PathVariable Long id);
}