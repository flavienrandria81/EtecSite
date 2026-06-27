package com.etudiant.empoiDuTemps.client;

import com.etudiant.empoiDuTemps.dto.EnseignantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ENSEIGNANT")
public interface EnseignantClient {

    @GetMapping("/enseignant/{id}")
    EnseignantDto getEnseignant(@PathVariable Long id);
}
