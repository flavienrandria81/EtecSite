package com.note.notification.dto;

import lombok.Data;

@Data
public class NotificationRequest {
    private Long userId;

    private String titre;
    private String message;

}
