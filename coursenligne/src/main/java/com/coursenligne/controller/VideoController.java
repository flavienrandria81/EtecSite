package com.coursenligne.controller;


import com.coursenligne.entity.Video;
import com.coursenligne.service.VideoService;

import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {



    private final VideoService videoService;





    // Ajouter une vidéo Youtube/Vimeo/Google Drive

    @PostMapping
    public ResponseEntity<Video> creer(
            @RequestBody Video video
    ){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        videoService.creerVideo(video)
                );

    }







    // Upload vidéo locale

    @PostMapping(
            value="/upload",
            consumes="multipart/form-data"
    )
    public ResponseEntity<Video> upload(

            @RequestParam String titre,

            @RequestParam Integer duree,

            @RequestParam Long leconId,

            @RequestParam MultipartFile file

    ) throws IOException {


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        videoService.uploadVideo(
                                titre,
                                duree,
                                leconId,
                                file
                        )
                );

    }







    @GetMapping
    public ResponseEntity<List<Video>> getAll(){

        return ResponseEntity.ok(
                videoService.getAll()
        );

    }







    @GetMapping("/{id}")
    public ResponseEntity<Video> getById(
            @PathVariable Long id
    ){

        return ResponseEntity.ok(
                videoService.getById(id)
        );

    }








    @GetMapping("/lecon/{id}")
    public ResponseEntity<List<Video>> getByLecon(
            @PathVariable Long id
    ){

        return ResponseEntity.ok(
                videoService.getByLeconId(id)
        );

    }







    @PutMapping("/{id}")
    public ResponseEntity<Video> modifier(
            @PathVariable Long id,
            @RequestBody Video video
    ){

        return ResponseEntity.ok(
                videoService.modifierVideo(
                        id,
                        video
                )
        );

    }







    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id
    ){

        videoService.supprimerVideo(id);

        return ResponseEntity.noContent()
                .build();

    }

}