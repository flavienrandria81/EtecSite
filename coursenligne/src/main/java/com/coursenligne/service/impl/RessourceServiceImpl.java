package com.coursenligne.service.impl;


import com.coursenligne.entity.Leçon;
import com.coursenligne.entity.Ressource;

import com.coursenligne.repository.LeçonRepository;
import com.coursenligne.repository.RessourceRepository;

import com.coursenligne.service.RessourceService;


import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;



@Service
@RequiredArgsConstructor
public class RessourceServiceImpl
        implements RessourceService {



    private final RessourceRepository ressourceRepository;

    private final LeçonRepository leçonRepository;






    // Création ressource
    @Override
    public Ressource creerRessource(
            Ressource ressource) {



        Long leçonId =
                ressource.getLeçon()
                        .getId();



        Leçon leçon =
                leçonRepository.findById(leçonId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Leçon introuvable"
                                )
                        );



        ressource.setLeçon(leçon);



        // Date automatique
        ressource.setDateAjout(
                LocalDate.now()
        );



        return ressourceRepository.save(ressource);

    }


    

    // Modifier
    @Override
    public Ressource modifierRessource(
            Long id,
            Ressource ressource) {



        Ressource ancienne =
                ressourceRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Ressource introuvable"
                                )
                        );



        ancienne.setNom(
                ressource.getNom()
        );


        ancienne.setUrl(
                ressource.getUrl()
        );


        ancienne.setTaille(
                ressource.getTaille()
        );


        ancienne.setType(
                ressource.getType()
        );



        return ressourceRepository.save(ancienne);

    }



    // Toutes les ressources
    @Override
    public List<Ressource> getAll() {


        return ressourceRepository.findAll();

    }


    // Une ressource
    @Override
    public Ressource getById(Long id) {


        return ressourceRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Ressource introuvable"
                        )
                );

    }





    // Ressources d'une leçon
    @Override
    public List<Ressource> getByLeconId(
            Long leçonId) {


        return ressourceRepository
                .findByLeçonId(leçonId);

    }




    // Supprimer
    @Override
    public void supprimerRessource(
            Long id) {


        Ressource ressource =
                getById(id);


        ressourceRepository.delete(ressource);

    }

}