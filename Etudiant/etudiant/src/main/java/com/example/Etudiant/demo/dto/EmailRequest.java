package com.example.Etudiant.demo.dto;

import lombok.Data;

@Data
public class EmailRequest {

    private String to;

    private String subject;

    private String message;

    private byte[] attachment;

    private String fileName;
}
