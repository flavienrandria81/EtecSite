package com.coursenligne.client;

import com.coursenligne.dto.DomaineDTO;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DOMAINE")
public interface DomaineClient {

    @GetMapping("/api/domaines/{id}")
    DomaineDTO getDomaineById(@PathVariable Long id);
}
