package com.visio.client;

import com.visio.config.FeignConfig;
import com.visio.dto.CoursDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COURSENLIGNE", configuration = FeignConfig.class)
public interface CoursClient {

    @GetMapping("/cours/{id}")
    CoursDto getCoursById(@PathVariable Long id);
}
