package com.example.Etudiant.demo.client;

import com.example.Etudiant.demo.config.FeignConfig;
import com.example.Etudiant.demo.dto.FiliereDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "FILIERES",  configuration = FeignConfig.class)
public interface FiliereClient {

    @GetMapping("/api/filieres/{id}")
    FiliereDto getFiliere(@PathVariable Long id);
}