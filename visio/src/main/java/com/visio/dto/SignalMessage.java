package com.visio.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignalMessage {

    private String type;
    private String from;
    private String to;
    private String roomCode;
    private String payload;
}
