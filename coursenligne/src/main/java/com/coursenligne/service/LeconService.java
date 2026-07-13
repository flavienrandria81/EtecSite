package com.coursenligne.service;


import com.coursenligne.entity.Lecon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface LeconService {


    // Créer une leçon
    Lecon creerLecon(Lecon leçon);



    // Modifier une leçon
    Lecon modifierLecon(Long id, Lecon leçon);



    // Récupérer toutes les leçons
    Page<Lecon> getAll(Pageable pageable);



    // Récupérer une leçon par id
    Lecon getById(Long id);



    // Récupérer les leçons d'un chapitre
    List<Lecon> getByChapitreId(Long chapitreId);



    // Supprimer une leçon
    void supprimerLecon(Long id);

}