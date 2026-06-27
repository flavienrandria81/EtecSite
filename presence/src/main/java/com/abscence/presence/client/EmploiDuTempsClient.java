package com.abscence.presence.client;

import com.abscence.presence.dto.EmploiDuTempsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "EMPLOIDUTEMPS")
public interface EmploiDuTempsClient {

    @GetMapping("/api/emploiDuTemps/{id}")
    EmploiDuTempsDto getEmploiDuTemps(@PathVariable Long id);
}
