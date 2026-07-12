package com.coursenligne.service.impl;

import com.coursenligne.client.EnseignantClient;
import com.coursenligne.client.FiliereClient;
import com.coursenligne.client.MatiereClient;
import com.coursenligne.client.NiveauClient;

import com.coursenligne.dto.*;


import com.coursenligne.entity.CoursEnLigne;

import com.coursenligne.repository.CoursEnLigneRepository;
import com.coursenligne.service.CoursEnLigneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CoursEnLigneServiceImpl implements CoursEnLigneService {


    private final CoursEnLigneRepository repository;


    private final EnseignantClient enseignantClient;

    private final MatiereClient matiereClient;

    private final FiliereClient filiereClient;

    private final NiveauClient niveauClient;






    // Création d'un cours
    @Override
    public CoursEnLigne creerCours(
            CoursEnLigne cours) {


        // Vérifier l'existence de l'enseignant
        EnseignantDTO enseignant =
                enseignantClient.getEnseignantById(
                        cours.getEnseignantId()
                );


        if(enseignant == null){
            throw new RuntimeException(
                    "Enseignant introuvable"
            );
        }



        // Vérifier matière
        MatiereDTO matiere =
                matiereClient.getMatiereById(
                        cours.getMatiereId()
                );


        if(matiere == null){
            throw new RuntimeException(
                    "Matière introuvable"
            );
        }



        // Vérifier filière
        FiliereDTO filiere =
                filiereClient.getFiliereById(
                        cours.getFiliereId()
                );


        if(filiere == null){
            throw new RuntimeException(
                    "Filière introuvable"
            );
        }



        // Vérifier niveau
        NiveauDTO niveau =
                niveauClient.getNiveauById(
                        cours.getNiveauId()
                );


        if(niveau == null){
            throw new RuntimeException(
                    "Niveau introuvable"
            );
        }



        return repository.save(cours);
    }





    // Pagination des cours
    @Override
    public Page<CoursEnLigne> getAll(
            Pageable pageable) {


        return repository.findAll(pageable);
    }





    // Recherche simple par ID
    @Override
    public CoursEnLigne getById(
            Long id) {


        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Cours introuvable avec id : "
                                        + id
                        ));
    }





    // Suppression
    @Override
    public void delete(
            Long id) {


        CoursEnLigne cours =
                getById(id);


        repository.delete(cours);
    }





    // Recherche avec les informations externes
    @Override
    public CoursResponse getCoursById(
            Long id) {


        CoursEnLigne cours =
                getById(id);



        EnseignantDTO enseignant =
                enseignantClient.getEnseignantById(
                        cours.getEnseignantId()
                );


        MatiereDTO matiere =
                matiereClient.getMatiereById(
                        cours.getMatiereId()
                );


        FiliereDTO filiere =
                filiereClient.getFiliereById(
                        cours.getFiliereId()
                );


        NiveauDTO niveau =
                niveauClient.getNiveauById(
                        cours.getNiveauId()
                );



        CoursResponse response =
                new CoursResponse();


        response.setId(
                cours.getId()
        );


        response.setTitre(
                cours.getTitre()
        );


        response.setDescription(
                cours.getDescription()
        );


        response.setEnseignant(
                enseignant
        );


        response.setMatiere(
                matiere
        );


        response.setFiliere(
                filiere
        );


        response.setNiveau(
                niveau
        );


        return response;
    }

}