package com.etec.slides.controller;

import com.etec.slides.entity.Slide;
import com.etec.slides.service.SlideService;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/slides")
@CrossOrigin("*")
@RequiredArgsConstructor
public class SlideController {

    private final SlideService slideService;

    @PostMapping(
            value = "/save",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Slide save(
            @RequestParam String titre,
            @RequestParam String description,
            @RequestParam Boolean active,
            @RequestParam MultipartFile file
    ) {
        return slideService.save(
                titre,
                description,
                active,
                file
        );
    }

    @GetMapping
    public Page<Slide> getAll(
            Pageable pageable
    ) {
        return slideService.findAll(pageable);
    }

    /*
     * =========================================================
     * AFFICHER LES IMAGES
     * =========================================================
     *
     * Les fichiers sont enregistrés dans :
     *
     * upload/
     *
     * Exemple :
     *
     * upload/abc123_image.jpg
     *
     * Cette route permet à React de récupérer cette image.
     */
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(
            @PathVariable String filename
    ) {

        try {

            Path filePath = Paths
                    .get("upload")
                    .resolve(filename)
                    .normalize();

            Resource resource =
                    new UrlResource(
                            filePath.toUri()
                    );

            if (!resource.exists()
                    || !resource.isReadable()) {

                return ResponseEntity
                        .notFound()
                        .build();
            }

            String contentType =
                    Files.probeContentType(filePath);

            if (contentType == null) {
                contentType =
                        MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            return ResponseEntity
                    .ok()
                    .contentType(
                            MediaType.parseMediaType(
                                    contentType
                            )
                    )
                    .body(resource);

        } catch (
                IOException e
        ) {

            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/{id}")
    public Slide getById(
            @PathVariable Long id
    ) {
        return slideService.findById(id);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Slide update(
            @PathVariable Long id,
            @RequestParam String titre,
            @RequestParam String description,
            @RequestParam Boolean active,
            @RequestParam(required = false)
            MultipartFile file
    ) {
        return slideService.update(
                id,
                titre,
                description,
                active,
                file
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                slideService.delete(id)
        );
    }
}