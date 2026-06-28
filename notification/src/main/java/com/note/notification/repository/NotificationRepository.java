package com.note.notification.repository;


import com.note.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification>
    findByUserIdOrderByDateCreationDesc(Long userId);

    List<Notification>
    findByUserIdAndLuFalse(Long userId);

}
