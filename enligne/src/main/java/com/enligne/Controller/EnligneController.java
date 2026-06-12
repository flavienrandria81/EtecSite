package com.enligne.Controller;

import com.enligne.EnligneService.EnligneService;
import com.enligne.Entity.Enligne;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formation_enligne")
@RequiredArgsConstructor
public class EnligneController {

    public final EnligneService enligneService;

    @PostMapping
    public Enligne create(@RequestBody Enligne enligne) {
        return enligneService.save(enligne);
    }

    @GetMapping({"/id"})
    public ResponseEntity<Enligne> getById(@PathVariable Long id_video){
        return enligneService.getById(id_video)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Enligne> getAll() {
        return enligneService.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enligne> update(@PathVariable Long id_video,
                                            @RequestBody Enligne encadreur) {
        return enligneService.getById(id_video)
                .map(existing -> {
                    encadreur.setId_video(id_video);
                    return ResponseEntity.ok(enligneService.save(encadreur));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id_video) {
        return enligneService.getById(id_video)
                .map(existing -> {
                    enligneService.delete(id_video);
                    return ResponseEntity.<Void>ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }


}
