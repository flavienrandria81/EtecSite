package com.visio.client;

import com.visio.config.FeignConfig;
import com.visio.dto.MatiereDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MATIERE", configuration = FeignConfig.class)
public interface MatiereClient {

    @GetMapping("/api/matieres/{id}")
    MatiereDto getMatiereById(@PathVariable Long id);
}
