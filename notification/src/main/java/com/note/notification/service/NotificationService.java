package com.note.notification.service;

import com.note.notification.dto.NotificationRequest;
import com.note.notification.entity.Notification;
import com.note.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class NotificationService {


    private final NotificationRepository repository;

    private final SimpMessagingTemplate messagingTemplate;


    public Notification envoyer(
            NotificationRequest request){

        Notification notification =
                new Notification();

        notification.setUserId(
                request.getUserId()
        );

        notification.setTitre(
                request.getTitre()
        );

        notification.setMessage(
                request.getMessage()
        );

        Notification saved =
                repository.save(notification);

        // Envoi temps réel WebSocket

        messagingTemplate.convertAndSend(

                "/topic/user/"
                        + saved.getUserId(),

                saved

        );

        return saved;
    }


    public List<Notification> getUserNotifications(
            Long userId){


        return repository
                .findByUserIdOrderByDateCreationDesc(
                        userId
                );
    }

    public Notification lire(Long id){

        Notification n =
                repository.findById(id)
                        .orElseThrow();

        n.setLu(true);

        return repository.save(n);

    }

}
