package com.visio.client;

import com.visio.config.FeignConfig;
import com.visio.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UTILISATEUR", configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("/api/auth/users/{id}")
    UserDto getUserById(@PathVariable Long id);
}
