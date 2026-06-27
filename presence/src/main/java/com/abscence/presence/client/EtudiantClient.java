package com.abscence.presence.client;

import com.abscence.presence.dto.EtudiantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ETUDIANT")
public interface EtudiantClient {

    @GetMapping("/etudiants/{id}")
    EtudiantDto getEtudiant(@PathVariable Long id);
}
