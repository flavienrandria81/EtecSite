package com.etudiant.note.client;

import com.etudiant.note.Dto.SemestreDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SEMESTRE")
public interface SemestreClient {
        @GetMapping("/api/semestres/{id}")
        SemestreDto getSemestre(@PathVariable Long id);
}
