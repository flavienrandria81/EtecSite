package com.encadrement.demo.client;

import com.encadrement.demo.dto.EnseignantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ENSEIGNANT")
public interface EnseignantClient {

    @GetMapping("/api/Enseignants/{id}")
    EnseignantDto getEnseignant(@PathVariable Long id);
}
