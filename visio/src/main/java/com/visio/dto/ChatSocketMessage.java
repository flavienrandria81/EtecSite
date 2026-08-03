package com.visio.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatSocketMessage {

    private String from;
    private String nom;
    private String role;
    private String message;
}
