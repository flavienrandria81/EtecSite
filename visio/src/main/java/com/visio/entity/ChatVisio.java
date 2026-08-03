package com.visio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_visio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatVisio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long salleVisioId;

    private Long utilisateurId;

    private String nom;

    @Column(length = 2000)
    private String message;

    @Builder.Default
    private LocalDateTime dateEnvoi = LocalDateTime.now();
}
