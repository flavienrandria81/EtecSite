package com.etudiant.empoiDuTemps.client;

import com.etudiant.empoiDuTemps.dto.FiliereDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "FILIERES")
public interface FiliereClient {

    @GetMapping("/api/filieres/{id}")
    FiliereDto getFiliere(@PathVariable Long id);
}
