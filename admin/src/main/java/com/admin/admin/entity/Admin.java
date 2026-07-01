package com.admin.admin.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cet ID correspond à l'ID généré dans la table 'users' du User Service
    private Long userId;

    private String firstName;
    private String lastName;
    private String phoneNumber;
}
