package com.memoire.memoire;

import com.memoire.memoire.dto.FilieresDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "FILIERE")
public interface FilieresClient {

    @GetMapping("/api/filieres/{id}")
    FilieresDto getFiliere(@PathVariable Long id);
}
