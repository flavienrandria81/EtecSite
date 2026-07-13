package com.coursenligne.service.impl;

import com.coursenligne.entity.Lecon;
import com.coursenligne.entity.Ressource;
import com.coursenligne.repository.LeconRepository;
import com.coursenligne.repository.RessourceRepository;
import com.coursenligne.service.RessourceService;


import jakarta.persistence.EntityNotFoundException;


import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;



@Service
@RequiredArgsConstructor
public class RessourceServiceImpl
        implements RessourceService {



    private final RessourceRepository ressourceRepository;

    private final LeconRepository leconRepository;





    @Override
    public Ressource creerRessource(
            Ressource ressource
    ) {


        if(ressource.getLecon() == null
                || ressource.getLecon().getId() == null){

            throw new IllegalArgumentException(
                    "La leçon est obligatoire"
            );
        }



        Lecon lecon =
                leconRepository.findById(
                                ressource.getLecon().getId()
                        )
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Leçon introuvable"
                                )
                        );



        ressource.setLecon(lecon);



        if(ressource.getDateAjout() == null){
            ressource.setDateAjout(
                    LocalDate.now()
            );
        }



        return ressourceRepository.save(ressource);

    }






    @Override
    public Ressource modifierRessource(
            Long id,
            Ressource ressource
    ) {


        Ressource existante =
                getById(id);



        existante.setNom(
                ressource.getNom()
        );


        existante.setUrl(
                ressource.getUrl()
        );


        existante.setTaille(
                ressource.getTaille()
        );


        existante.setType(
                ressource.getType()
        );



        return ressourceRepository.save(existante);

    }








    @Override
    public Page<Ressource> getAll(
            Pageable pageable
    ) {

        return ressourceRepository.findAll(pageable);

    }








    @Override
    public Ressource getById(Long id) {


        return ressourceRepository.findById(id)

                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Ressource introuvable avec id : "
                                        + id
                        )
                );

    }




    @Override
    public List<Ressource> getByLeconId(
            Long leconId
    ) {


        return ressourceRepository
                .findByLeconId(leconId);

    }








    @Override
    public void supprimerRessource(
            Long id
    ) {


        Ressource ressource =
                getById(id);


        ressourceRepository.delete(ressource);

    }

}