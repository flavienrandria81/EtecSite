package com.coursenligne.service.impl;


import com.coursenligne.entity.Leçon;
import com.coursenligne.entity.Video;

import com.coursenligne.repository.LeçonRepository;
import com.coursenligne.repository.VideoRepository;

import com.coursenligne.service.VideoService;


import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;



@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {


    private final VideoRepository videoRepository;

    private final LeçonRepository leçonRepository;




    // Création vidéo
    @Override
    public Video creerVideo(Video video) {


        Long leçonId =
                video.getLeçon()
                        .getId();



        Leçon leçon =
                leçonRepository.findById(leçonId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Leçon introuvable"
                                )
                        );



        video.setLeçon(leçon);



        return videoRepository.save(video);
    }







    // Modification
    @Override
    public Video modifierVideo(
            Long id,
            Video video) {


        Video ancienne =
                videoRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Vidéo introuvable"
                                )
                        );



        ancienne.setTitre(
                video.getTitre()
        );


        ancienne.setUrlVideo(
                video.getUrlVideo()
        );


        ancienne.setDuree(
                video.getDuree()
        );



        return videoRepository.save(ancienne);
    }








    // Toutes les vidéos
    @Override
    public List<Video> getAll() {

        return videoRepository.findAll();

    }








    // Une vidéo
    @Override
    public Video getById(Long id) {


        return videoRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Vidéo introuvable"
                        )
                );

    }

    @Override
    public List<Video> getByLeconId(Long leçonId) {
        return List.of();
    }


    // Vidéos d'une leçon
    public Page<Video> getByLeconId(
            Long leçonId, Pageable pageable) {


        return videoRepository
                .findByLeçonId(leçonId, pageable);

    }


    
    // Suppression
    @Override
    public void supprimerVideo(Long id) {


        Video video =
                getById(id);


        videoRepository.delete(video);

    }

}