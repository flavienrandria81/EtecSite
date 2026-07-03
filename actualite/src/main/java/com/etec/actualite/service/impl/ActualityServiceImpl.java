package com.etec.actualite.service.impl;

import com.etec.actualite.entity.Actuality;
import com.etec.actualite.entity.Categorie;
import com.etec.actualite.entity.Status;
import com.etec.actualite.repository.ActualiteRepository;
import com.etec.actualite.service.ActualiteService;
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
public class ActualityServiceImpl implements ActualiteService {

    private final ActualiteRepository repository;

    // 🎯 On s'assure que le chemin absolu se termine bien par le séparateur de fichier de l'OS (\ ou /)
    private final String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

    @Override
    public Actuality save(String titre, String description, Status status, Categorie categorie, MultipartFile file) {
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filename = null;
            if (file != null && !file.isEmpty()) {
                filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR, filename);
                System.out.println("================================");
                System.out.println("UPLOAD_DIR = " + UPLOAD_DIR);
                System.out.println("PATH = " + path.toAbsolutePath());
                System.out.println("Nom original = " + file.getOriginalFilename());
                System.out.println("Taille = " + file.getSize());

                Files.write(path, file.getBytes());

                System.out.println("Le fichier existe ? " + Files.exists(path));
                System.out.println("================================");
            }

            Actuality actuality = new Actuality();
            actuality.setTitre(titre);
            actuality.setDescription(description);
            actuality.setStatus(status);
            actuality.setCategorie(categorie);
            actuality.setImage(filename);

            return repository.save(actuality);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création de l'image");
        }
    }

    @Override
    public Page<Actuality> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Actuality findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actualité introuvable"));
    }

    @Override
    public Actuality update(Long id, String titre, String description, Status status, Categorie categorie, MultipartFile file) {

        Actuality existing = findById(id);

        existing.setTitre(titre);
        existing.setDescription(description);
        existing.setCategorie(categorie);
        existing.setStatus(status);

        try {
            if (file != null && !file.isEmpty()) {
                // 1. On supprime l'ancienne image SI elle existe
                if (existing.getImage() != null) {
                    File old = new File(UPLOAD_DIR + existing.getImage());
                    if (old.exists()) {
                        old.delete();
                    }
                }

                // 2. On enregistre TOUJOURS la nouvelle image, qu'il y en ait eu une ancienne ou pas !
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR, filename);
                Files.write(path, file.getBytes());

                existing.setImage(filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la modification de l'image : " + e.getMessage());
        }

        return repository.save(existing);
    }

    @Override
    public String delete(Long id) {

        Actuality actuality = findById(id);

        try {
            if (actuality.getImage() != null) {
                // 🎯 CORRECTION : Le chemin est maintenant propre grâce au File.separator global
                File file = new File(UPLOAD_DIR + actuality.getImage());
                if (file.exists()) {
                    file.delete();
                }
            }
            repository.deleteById(id);
            return "Actualité supprimée avec succès";
        } catch (Exception e) {
            return "Erreur lors de la suppression";
        }
    }
}