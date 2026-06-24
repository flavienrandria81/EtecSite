package com.example.Enseignant.demo.Service;

import com.example.Enseignant.demo.Entity.Enseignant;
import com.example.Enseignant.demo.Repository.EnseignantRepository;
import com.example.Enseignant.demo.client.FiliereClient;
import com.example.Enseignant.demo.client.MatiereClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final FiliereClient filiereClient;
    private final MatiereClient matiereClient;
    private final String UPLOAD_DIR = "/upload";


    @Transactional
    public Enseignant createEnseignant(String matricule,
                                       String adresse,
                                       String phone,
                                       String cin,
                                       String specialite,
                                       Long filiereId,
                                       Long matiereId,
                                       MultipartFile photo,
                                       MultipartFile diplome,
                                       HttpServletRequest request) {

        try {
            File dir = new File(UPLOAD_DIR);
            if (dir.exists()) {
                dir.mkdirs();
            }

            String photoName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
            Path photoPath = Paths.get(UPLOAD_DIR, photoName);
            Files.write(photoPath, photo.getBytes());

            String diplomeName = UUID.randomUUID() + "_" + diplome.getOriginalFilename();
            Path diplomePath = Paths.get(UPLOAD_DIR, diplomeName);
            Files.write(diplomePath, diplome.getBytes());

            Enseignant enseignant = new Enseignant();
            if (enseignantRepository.existsByMatricule(enseignant.getMatricule())) {
                throw new RuntimeException("Un enseignant avec ce matricule existe déjà.");
            }

            enseignant.setMatricule(matricule);
            enseignant.setAdresse(adresse);
            enseignant.setPhone(phone);
            enseignant.setCin(cin);
            enseignant.setSpecialite(specialite);
            enseignant.setPhoto(photoName);
            enseignant.setDiplome(diplomeName);

            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                throw new RuntimeException("UserId introvable dans le token");
            }

            enseignant.setUserId(userId);
            enseignant.setFiliereId(filiereId);
            enseignant.setMatiereId(matiereId);

            try {
                filiereClient.getFiliere(filiereId);
                matiereClient.getMatiere(matiereId);
            } catch (Exception e) {
                throw new RuntimeException("Filière ou Matière introuvable");
            }
            return enseignantRepository.save(enseignant);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de créer ces fichiers");
        }

    }

    public Page<Enseignant> getAllEnseignants(Pageable pageable) {
        return enseignantRepository.findAll(pageable);
    }

    public Enseignant getEnseignantById(Long id) {
        return enseignantRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Enseignant Introuvable"));
    }

    public Enseignant getByMatricule(String matricule) {
        return enseignantRepository.findByMatricule(matricule)
                .orElseThrow(() -> new RuntimeException("Matricule introuvable"));
    }


    public Enseignant updateEnseignant(Long id,
                                       String matricule,
                                       String adresse,
                                       String phone,
                                       String cin,
                                       String specialite,
                                       MultipartFile photo,
                                       MultipartFile diplome ) {

        Enseignant existing = getEnseignantById(id);
        // Mise à jour de tous les champs de ton entité
        existing.setMatricule(matricule);
        existing.setAdresse(adresse);
        existing.setPhone(phone);
        existing.setCin(cin);
        existing.setSpecialite(specialite);

        try {
            if (photo != null && !photo.isEmpty()) {
                if (existing.getPhoto() != null) {
                    File old = new File(UPLOAD_DIR + existing.getPhoto());
                    if (old.exists()) old.delete();

                    String photoName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
                    Path path = Paths.get(UPLOAD_DIR, photoName);
                    Files.write(path, photo.getBytes());

                    existing.setPhoto(photoName);
                }
            }

            if (diplome != null && !diplome.isEmpty()) {
                if (existing.getDiplome() != null) {
                    File old = new File(UPLOAD_DIR + existing.getDiplome());
                    if (old.exists()) old.delete();

                    String diplomeName = UUID.randomUUID() + "_" + diplome.getOriginalFilename();
                    Path path = Paths.get(UPLOAD_DIR, diplomeName);
                    Files.write(path, diplome.getBytes());

                    existing.setDiplome(diplomeName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de modifier ces fichiers");
        }

        return enseignantRepository.save(existing);
    }

    public void deleteEnseignant(Long id) {
        if (!enseignantRepository.existsById(id)) {
            throw new RuntimeException("Enseignant introuvable avec l'ID : " + id);
        }
        enseignantRepository.deleteById(id);
    }

}
