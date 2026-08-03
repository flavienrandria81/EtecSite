package com.visio.client;

import com.visio.config.FeignConfig;
import com.visio.dto.EtudiantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ETUDIANT", configuration = FeignConfig.class)
public interface EtudiantClient {

    @GetMapping("/etudiants/{id}")
    EtudiantDto getEtudiantById(@PathVariable Long id);

    @GetMapping("/etudiants/me")
    EtudiantDto getMonProfil();
}
