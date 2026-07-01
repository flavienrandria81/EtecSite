package com.memoire.memoire.service.impl;

import com.common.common.dto.NotificationRequest;
import com.memoire.memoire.FilieresClient;
import com.memoire.memoire.client.*;
import com.memoire.memoire.entity.Memoire;
import com.memoire.memoire.entity.StatutMemoire;
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
    private final EtudiantClient etudiantClient;
    private final NotificationClient notificationClient;
    private final UserClient userClient;
    private final NoteClient noteClient;
    private final EnseignantClient enseignantClient;
    private final FilieresClient filieresClient;
    private final NiveauClient niveauClient;
    private final DomaineClient domaineClient;
    private final String UPLOAD_DIR = "/upload";
    @Override
    public Memoire save(Long etudiantId,
                        Long userId,
                        Long noteId,
                        Long enseignantId,
                        Long filiereId,
                        Long niveauId,
                        Long domaineId,
                        String theme,
                        String description,
                        StatutMemoire statutMemoire,
                        MultipartFile file) {

        etudiantClient.findById(etudiantId);
        userClient.findById(userId);
        noteClient.findById(noteId);
        enseignantClient.findById(enseignantId);
        filieresClient.getFiliere(filiereId);
        niveauClient.getNiveau(niveauId);
        domaineClient.getDomaine(domaineId);


        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String livrename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR, livrename);
            Files.write(path, file.getBytes());

            Memoire memoire = new Memoire();
            memoire.setEtudiantId(etudiantId);
            memoire.setUserId(userId);
            memoire.setNoteId(noteId);
            memoire.setEnsignantId(enseignantId);
            memoire.setTheme(theme);
            memoire.setDescription(description);
            memoire.setStatut(statutMemoire);
            memoire.setLivre(livrename);


            Memoire saved = repository.save(memoire);

            NotificationRequest request = new NotificationRequest();

            request.setUserId(userId);
            request.setEtudiantId(etudiantId);
            request.setTitre("Mémoire déposé");
            request.setMessage("Votre mémoire \"" + theme + "\" a été déposé avec succès.");

            notificationClient.envoyer(request);

            return saved;

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
    public Memoire update(Long id, String theme, String description, StatutMemoire statutMemoire, MultipartFile file) {
        Memoire existing = findById(id);

        existing.setTheme(theme);
        existing.setDescription(description);
        existing.setStatut(statutMemoire.BROUILLON);

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
