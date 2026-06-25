package com.etudiant.empoiDuTemps.client;

import com.etudiant.empoiDuTemps.dto.MatiereDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MATIERE")
public interface MatiereClient {

    @GetMapping("/api/matieres/{id}")
    MatiereDto getMiatiere(@PathVariable Long id);
}
