package com.visio.client;

import com.common.common.dto.NotificationRequest;
import com.visio.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NOTIFICATION", configuration = FeignConfig.class)
public interface NotificationClient {

    @PostMapping("/notifications/send")
    Object envoyer(@RequestBody NotificationRequest request);
}
