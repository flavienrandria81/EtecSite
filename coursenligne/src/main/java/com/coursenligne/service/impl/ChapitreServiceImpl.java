package com.coursenligne.service.impl;

import com.coursenligne.entity.Chapitre;
import com.coursenligne.entity.CoursEnLigne;
import com.coursenligne.repository.ChapitreRepository;
import com.coursenligne.repository.CoursEnLigneRepository;
import com.coursenligne.service.ChapitreService;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ChapitreServiceImpl implements ChapitreService {


    private final ChapitreRepository chapitreRepository;

    private final CoursEnLigneRepository coursRepository;



    @Override
    public Chapitre creerChapitre(Chapitre chapitre) {


        if(chapitre.getCours() == null
                || chapitre.getCours().getId() == null){

            throw new IllegalArgumentException(
                    "Le cours est obligatoire"
            );
        }


        CoursEnLigne cours =
                coursRepository.findById(
                                chapitre.getCours().getId()
                        )
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Cours introuvable"
                                ));


        chapitre.setCours(cours);


        return chapitreRepository.save(chapitre);
    }




    @Override
    public Chapitre modifierChapitre(Long id, Chapitre chapitre) {


        Chapitre existant =
                getById(id);


        existant.setTitre(
                chapitre.getTitre()
        );


        existant.setDescription(
                chapitre.getDescription()
        );


        existant.setOrdre(
                chapitre.getOrdre()
        );


        return chapitreRepository.save(existant);
    }




    @Override
    public List<Chapitre> getAll() {

        return chapitreRepository.findAll();
    }




    @Override
    public Chapitre getById(Long id) {


        return chapitreRepository.findById(id)

                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Chapitre introuvable avec id : "
                                        + id
                        )
                );
    }





    @Override
    public List<Chapitre> getByCoursId(Long coursId) {


        return chapitreRepository
                .findByCoursIdOrderByOrdreAsc(coursId);
    }





    @Override
    public void supprimerChapitre(Long id) {


        Chapitre chapitre =
                getById(id);


        chapitreRepository.delete(chapitre);
    }

}