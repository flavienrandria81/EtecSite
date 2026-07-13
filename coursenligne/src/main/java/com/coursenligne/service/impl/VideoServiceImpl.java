package com.coursenligne.service.impl;



import com.coursenligne.entity.Lecon;
import com.coursenligne.entity.TypeVideo;
import com.coursenligne.entity.Video;
import com.coursenligne.repository.LeconRepository;
import com.coursenligne.repository.VideoRepository;

import com.coursenligne.service.VideoService;


import jakarta.persistence.EntityNotFoundException;


import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.UUID;



@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {


    private final VideoRepository videoRepository;

    private final LeconRepository leconRepository;


    private final String uploadDir =
            "uploads/videos/";



    @Override
    public Video creerVideo(Video video) {


        if(video.getLecon()==null
                || video.getLecon().getId()==null){

            throw new IllegalArgumentException(
                    "La leçon est obligatoire"
            );
        }


        Lecon lecon =
                leconRepository.findById(
                                video.getLecon().getId()
                        )
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Leçon introuvable"
                                )
                        );


        video.setLecon(lecon);


        return videoRepository.save(video);

    }






    /*
       Upload d'une vidéo locale
    */

    public Video uploadVideo(
            String titre,
            Integer duree,
            Long leconId,
            MultipartFile file
    ) throws IOException {



        Lecon lecon =
                leconRepository.findById(leconId)

                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Leçon introuvable"
                                )
                        );



        Path dossier =
                Paths.get(uploadDir);



        if(!Files.exists(dossier)){
            Files.createDirectories(dossier);
        }



        String nomFichier =
                UUID.randomUUID()
                        +"_"
                        +file.getOriginalFilename();



        Path chemin =
                dossier.resolve(nomFichier);



        Files.copy(
                file.getInputStream(),
                chemin
        );



        Video video = new Video();


        video.setTitre(titre);

        video.setDuree(duree);

        video.setType(TypeVideo.UPLOAD);


        video.setUrlVideo(
                "/uploads/videos/"
                        +nomFichier
        );


        video.setLecon(lecon);



        return videoRepository.save(video);

    }


    @Override
    public Video modifierVideo(
            Long id,
            Video video
    ){


        Video existante =
                getById(id);



        existante.setTitre(
                video.getTitre()
        );


        existante.setDuree(
                video.getDuree()
        );


        existante.setUrlVideo(
                video.getUrlVideo()
        );


        existante.setType(
                video.getType()
        );



        return videoRepository.save(existante);

    }







    @Override
    public List<Video> getAll(){

        return videoRepository.findAll();

    }







    @Override
    public Video getById(Long id){


        return videoRepository.findById(id)

                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Vidéo introuvable"
                        )
                );

    }








    @Override
    public List<Video> getByLeconId(
            Long leconId
    ){

        return videoRepository
                .findByLeconId(
                        leconId,
                        Pageable.unpaged()
                )
                .getContent();

    }







    @Override
    public void supprimerVideo(Long id){

        Video video =
                getById(id);


        videoRepository.delete(video);

    }

}