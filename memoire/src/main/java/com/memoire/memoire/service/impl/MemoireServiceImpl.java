package com.memoire.memoire.service.impl;

import com.memoire.memoire.entity.Memoire;
import com.memoire.memoire.repository.MemoireRepository;
import com.memoire.memoire.service.MemoireService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemoireServiceImpl implements MemoireService {

    private final MemoireRepository repository;
    private final String UPLOAD_DIR = "/upload";
    @Override
    public Memoire save(String theme, String description, MultipartFile file) {
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String livrename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR, livrename);
            Files.write(path, file.getBytes());

            Memoire memoire = new Memoire();
            memoire.setTheme(theme);
            memoire.setDescription(description);
            memoire.setLivre(livrename);

            return repository.save(memoire);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de créer ce fichier");
        }
    }

    @Override
    public Page<Memoire> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Memoire findById(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Memoire introuvable"));
    }

    @Override
    public Memoire update(Long id, String theme, String description, MultipartFile file) {
        Memoire existing = findById(id);

        existing.setTheme(theme);
        existing.setDescription(description);

        try {
            if (file !=  null && !file.isEmpty()) {
                if (existing.getLivre() != null) {
                    File old = new File(UPLOAD_DIR + existing.getLivre());
                    if (old.exists()) old.delete();
                }
            }

            String livreName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR, livreName);
            Files.write(path, file.getBytes());

            existing.setLivre(livreName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de modifier un fichier");
        }
        return repository.save(existing);
    }

    @Override
    public String delete(Long id) {
        Memoire memoire = findById(id);
        try {
            if (memoire.getLivre() != null) {
                File file = new File(UPLOAD_DIR + memoire.getLivre());
                if (file.exists()) {
                    boolean deleted = file.delete();
                    if (!deleted) {
                        return "Image non supprimer";
                    }
                }
            }
            repository.deleteById(id);
            return "Mémoire supprimer avec succès";
        }catch (Exception e) {
            return "Erreur de la suppression";
        }
    }
}
