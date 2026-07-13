package com.coursenligne.service;


import com.coursenligne.entity.Ressource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    Page<Ressource> getAll(Pageable pageable);



    // Récupérer une ressource par id
    Ressource getById(Long id);



    // Ressources d'une leçon
    List<Ressource> getByLeconId(Long leconId);



    // Supprimer une ressource
    void supprimerRessource(Long id);

}