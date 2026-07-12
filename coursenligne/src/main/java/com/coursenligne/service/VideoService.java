package com.coursenligne.service;


import com.coursenligne.entity.Video;

import java.util.List;


public interface VideoService {

    // Créer une vidéo
    Video creerVideo(Video video);
    // Modifier une vidéo
    Video modifierVideo(Long id, Video video);

    // Liste des vidéos
    List<Video> getAll();

    // Trouver une vidéo
    Video getById(Long id);

    // Vidéos d'une leçon
    List<Video> getByLeconId(Long leçonId);

    // Supprimer
    void supprimerVideo(Long id);

}