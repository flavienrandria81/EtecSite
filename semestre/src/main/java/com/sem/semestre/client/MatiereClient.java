package com.sem.semestre.client;

import com.sem.semestre.dto.MatiereDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MATIERE")
public interface MatiereClient {

    @GetMapping("/api/matieres/{id}")
    MatiereDto getMatiere(@PathVariable Long id);
}
