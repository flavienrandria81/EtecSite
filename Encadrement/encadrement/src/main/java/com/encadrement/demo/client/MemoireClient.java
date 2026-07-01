package com.encadrement.demo.client;

import com.encadrement.demo.dto.MemoireDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MEMOIRE")
public interface MemoireClient {

    @GetMapping("/api/memoires/{id}")
    MemoireDto getMemoire(@PathVariable Long id);
}
