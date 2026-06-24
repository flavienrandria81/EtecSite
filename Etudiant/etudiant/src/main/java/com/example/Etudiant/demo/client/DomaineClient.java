package com.example.Etudiant.demo.client;

import com.example.Etudiant.demo.dto.DomaineDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DOMAINE")
public interface DomaineClient {

    @GetMapping("/api/domaines")
    DomaineDto getDomaine(@PathVariable Long id);
}
