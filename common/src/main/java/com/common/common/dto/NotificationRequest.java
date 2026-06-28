package com.common.common.dto;


public class NotificationRequest {
    private Long userId;
    private String titre;
    private String message;
    public NotificationRequest() {
    }
    public NotificationRequest(
            Long userId,
            String titre,
            String message) {

        this.userId = userId;
        this.titre = titre;
        this.message = message;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitre() {
        return titre;
    }


    public void setTitre(String titre) {
        this.titre = titre;
    }



    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }

}