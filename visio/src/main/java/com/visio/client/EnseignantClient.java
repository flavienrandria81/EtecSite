package com.visio.client;

import com.visio.config.FeignConfig;
import com.visio.dto.EnseignantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ENSEIGNANT", configuration = FeignConfig.class)
public interface EnseignantClient {

    @GetMapping("/api/Enseignants/{id}")
    EnseignantDto getEnseignantById(@PathVariable Long id);
}
