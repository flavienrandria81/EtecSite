package com.visio.client;

import com.visio.config.FeignConfig;
import com.visio.dto.PresenceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PRESENCE", configuration = FeignConfig.class)
public interface PresenceClient {

    @PostMapping("/api/presences/save")
    Object enregistrerPresence(@RequestBody PresenceRequest request);
}
