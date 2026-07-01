package com.etudiant.note.client;

import com.etudiant.note.Dto.AnneesUnivDto;
import com.etudiant.note.Dto.DomaineDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UNIVERSITAIRE")
public interface AnneesUnivClient {

        @GetMapping("/api/anneesUniv/{id}")
        AnneesUnivDto getAnnees(@PathVariable Long id);
}
