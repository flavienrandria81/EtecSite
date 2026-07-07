package com.coursenligne.controller;


import com.coursenligne.entity.Video;
import com.coursenligne.service.VideoService;


import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {



    private final VideoService videoService;





    // Créer une vidéo
    @PostMapping
    public ResponseEntity<Video> creerVideo(
            @RequestBody Video video
    ){


        Video nouvelle =
                videoService.creerVideo(video);


        return new ResponseEntity<>(
                nouvelle,
                HttpStatus.CREATED
        );

    }







    // Modifier une vidéo
    @PutMapping("/{id}")
    public ResponseEntity<Video> modifierVideo(
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







    // Toutes les vidéos
    @GetMapping
    public ResponseEntity<List<Video>> getAll(){


        return ResponseEntity.ok(
                videoService.getAll()
        );

    }







    // Une vidéo par ID
    @GetMapping("/{id}")
    public ResponseEntity<Video> getById(
            @PathVariable Long id
    ){


        return ResponseEntity.ok(
                videoService.getById(id)
        );

    }







    // Vidéos d'une leçon
    @GetMapping("/lecon/{leçonId}")
    public ResponseEntity<List<Video>> getByLeconId(
            @PathVariable Long leçonId
    ){


        return ResponseEntity.ok(
                videoService.getByLeconId(leçonId)
        );

    }







    // Supprimer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerVideo(
            @PathVariable Long id
    ){


        videoService.supprimerVideo(id);


        return ResponseEntity.noContent()
                .build();

    }

}