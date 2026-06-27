package com.sem.semestre.client;

import com.sem.semestre.dto.AnneesUnivDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UNIVESITAIRE")
public interface UniversitaireClient {

    @GetMapping("/api/anneesUniv/{id}")
    AnneesUnivDto getAnneesUniv(@PathVariable Long id);
}
