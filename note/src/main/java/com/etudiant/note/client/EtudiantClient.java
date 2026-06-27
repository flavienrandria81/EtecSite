package com.etudiant.note.client;

import com.etudiant.note.Dto.EtudiantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ETUDIANT")
public interface EtudiantClient {

    @GetMapping("/etudiants/complet/{id}")
    EtudiantResponse getEtudiant(
            @PathVariable("id") Long id
    );

}
