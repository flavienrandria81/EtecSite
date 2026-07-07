package com.coursenligne.service;


import com.coursenligne.entity.Ressource;

import java.util.List;


public interface RessourceService {


    // Créer une ressource
    Ressource creerRessource(Ressource ressource);



    // Modifier une ressource
    Ressource modifierRessource(
            Long id,
            Ressource ressource
    );



    // Récupérer toutes les ressources
    List<Ressource> getAll();



    // Récupérer une ressource par id
    Ressource getById(Long id);



    // Ressources d'une leçon
    List<Ressource> getByLeconId(Long leçonId);



    // Supprimer une ressource
    void supprimerRessource(Long id);

}