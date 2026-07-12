package com.coursenligne.service.impl;


import com.coursenligne.entity.Chapitre;
import com.coursenligne.entity.CoursEnLigne;
import com.coursenligne.repository.ChapitreRepository;
import com.coursenligne.repository.CoursEnLigneRepository;
import com.coursenligne.service.ChapitreService;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.List;



@Service
@RequiredArgsConstructor
public class ChapitreServiceImpl implements ChapitreService {



    private final ChapitreRepository chapitreRepository;

    private final CoursEnLigneRepository coursRepository;





    // Création chapitre
    @Override
    public Chapitre creerChapitre(
            Chapitre chapitre) {


        Long coursId =
                chapitre.getCours()
                        .getId();



        CoursEnLigne cours =
                coursRepository.findById(coursId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Cours introuvable"
                                ));


        chapitre.setCours(cours);


        return chapitreRepository.save(chapitre);
    }






    // Modification
    @Override
    public Chapitre modifierChapitre(
            Long id,
            Chapitre chapitre) {


        Chapitre ancien =
                chapitreRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Chapitre introuvable"
                                ));



        ancien.setTitre(
                chapitre.getTitre()
        );


        ancien.setDescription(
                chapitre.getDescription()
        );


        ancien.setOrdre(
                chapitre.getOrdre()
        );



        return chapitreRepository.save(ancien);
    }







    // Tous les chapitres
    @Override
    public List<Chapitre> getAll() {

        return chapitreRepository.findAll();

    }








    // Un chapitre par id
    @Override
    public Chapitre getById(Long id) {


        return chapitreRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Chapitre introuvable"
                        ));

    }








    // Chapitres d'un cours
    @Override
    public List<Chapitre> getByCoursId(
            Long coursId) {


        return chapitreRepository
                .findByCoursIdOrderByOrdreAsc(
                        coursId
                );

    }








    // Suppression
    @Override
    public void supprimerChapitre(
            Long id) {


        Chapitre chapitre =
                getById(id);


        chapitreRepository.delete(chapitre);

    }

}