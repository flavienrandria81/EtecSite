package com.example.Etudiant.demo.client;

import com.example.Etudiant.demo.dto.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "EMAIL")
public interface EmailClient {

    @PostMapping("/emails/send")
    void sendEmail(
            @RequestBody EmailRequest request
    );

}