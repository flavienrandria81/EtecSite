package com.coursenligne.service.impl;


import com.coursenligne.entity.Chapitre;
import com.coursenligne.entity.Lecon;
import com.coursenligne.repository.ChapitreRepository;
import com.coursenligne.repository.LeconRepository;

import com.coursenligne.service.LeconService;
import jakarta.persistence.EntityNotFoundException;


import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;



@Service
@RequiredArgsConstructor
public class LeconServiceImpl implements LeconService {


    private final LeconRepository leconRepository;

    private final ChapitreRepository chapitreRepository;



    @Override
    public Lecon creerLecon(Lecon lecon) {


        if(lecon.getChapitre() == null
                || lecon.getChapitre().getId() == null){

            throw new IllegalArgumentException(
                    "Le chapitre est obligatoire"
            );
        }



        Chapitre chapitre =
                chapitreRepository.findById(
                                lecon.getChapitre().getId()
                        )
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Chapitre introuvable"
                                )
                        );



        lecon.setChapitre(chapitre);



        return leconRepository.save(lecon);
    }





    @Override
    public Lecon modifierLecon(
            Long id,
            Lecon lecon
    ) {


        Lecon existante =
                getById(id);



        existante.setTitre(
                lecon.getTitre()
        );


        existante.setContenu(
                lecon.getContenu()
        );


        existante.setDuree(
                lecon.getDuree()
        );



        return leconRepository.save(existante);
    }






    @Override
    public Page<Lecon> getAll(Pageable pageable) {

        return leconRepository.findAll(pageable);
    }






    @Override
    public Lecon getById(Long id) {


        return leconRepository.findById(id)

                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Leçon introuvable : "
                                        + id
                        )
                );
    }







    @Override
    public List<Lecon> getByChapitreId(
            Long chapitreId
    ) {


        return leconRepository
                .findByChapitreIdOrderByIdAsc(
                        chapitreId
                );
    }







    @Override
    public void supprimerLecon(Long id) {


        Lecon lecon =
                getById(id);


        leconRepository.delete(lecon);

    }

}