package com.example.Enseignant.demo.client;

import com.example.Enseignant.demo.dto.MatiereDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MATIERE")
public interface MatiereClient {
    @GetMapping("/api/matieres/{id}")
    MatiereDto getMatiere(@PathVariable Long id);
}
