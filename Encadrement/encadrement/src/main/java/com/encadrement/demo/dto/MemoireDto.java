package com.encadrement.demo.dto;

import lombok.Data;

@Data
public class MemoireDto {
        private Long id;
        private Long etudiantId;
        private Long userId;
        private Long noteId;
        private Long ensignantId;
        private Long filiereId;
        private Long niveauId;
        private Long domaineId;
        private String theme;
        private String description;
        private String livre;
}
