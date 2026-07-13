package com.coursenligne.service;


import com.coursenligne.entity.Video;

import java.util.List;


import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


public interface VideoService {


    Video creerVideo(Video video);


    Video uploadVideo(
            String titre,
            Integer duree,
            Long leconId,
            MultipartFile file
    ) throws IOException;



    Video modifierVideo(Long id, Video video);


    List<Video> getAll();


    Video getById(Long id);


    List<Video> getByLeconId(Long leconId);


    void supprimerVideo(Long id);

}