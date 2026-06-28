package com.note.notification.controller;

import com.note.notification.dto.NotificationRequest;
import com.note.notification.entity.Notification;
import com.note.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public Notification envoyer(
            @RequestBody NotificationRequest request){


        return service.envoyer(request);

    }


    @GetMapping("/user/{id}")
    public List<Notification> getUser(
            @PathVariable Long id){


        return service.getUserNotifications(id);

    }


    @PutMapping("/{id}/lire")
    public Notification lire(
            @PathVariable Long id){


        return service.lire(id);

    }

}
