package com.example.Etudiant.demo.service;

import com.example.Etudiant.demo.client.*;
import com.example.Etudiant.demo.dto.*;
import com.example.Etudiant.demo.entity.Etudiant;
import com.example.Etudiant.demo.entity.TypeFormation;
import com.example.Etudiant.demo.repository.EtudiantRepository;

import jakarta.servlet.http.HttpServletRequest;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final UserClient userClient;
    private final FiliereClient filiereClient;
    private final NiveauClient niveauClient;
    private final DomaineClient domaineClient;
    private final EmailClient emailClient;
    private final QrCodeService qrCodeService;
    private final String UPLOAD_DIR = "upload/";

    // CREATE ETUDIANT
    public Etudiant createEtudiant(String matricule,
                                   String cin,
                                   String adresse,
                                   String phone,
                                   TypeFormation typeFormation,
                                   Long filiereId,
                                   Long niveauId,
                                   Long domaineId,
                                   MultipartFile photo,
                                   MultipartFile releve,
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

            String releveName = UUID.randomUUID() + "_" + releve.getOriginalFilename();
            Path relevePath = Paths.get(UPLOAD_DIR, releveName);
            Files.write(relevePath, releve.getBytes());

            String diplomeName = UUID.randomUUID() + "_" + diplome.getOriginalFilename();
            Path diplomePath = Paths.get(UPLOAD_DIR, diplomeName);
            Files.write(diplomePath, diplome.getBytes());



            Etudiant etudiant = new Etudiant();
            if (etudiantRepository.existsByMatricule(matricule)) {
                throw new RuntimeException("Ce matricule est éxiste déjà");
            }
            etudiant.setMatricule(matricule);
            etudiant.setCin(cin);
            etudiant.setAdresse(adresse);
            etudiant.setPhone(phone);
            etudiant.setTypeFormation(typeFormation);
            etudiant.setPhoto(photoName);
            etudiant.setReleve(releveName);
            etudiant.setDiplome(diplomeName);

            // récupérer userId depuis JwtFilter
            Long userId = (Long) request.getAttribute("userId");


            if (userId == null) {
                throw new RuntimeException("userId introuvable dans le token");
            }

            // lien User, filiere, niveau ↔ Etudiant
            etudiant.setUserId(userId);
            etudiant.setFiliereId(filiereId);
            etudiant.setNiveauId(niveauId);
            etudiant.setDomaineId(domaineId);

            System.out.println("userId = " + userId);
            System.out.println("filiereId = " + filiereId);
            System.out.println("niveauId = " + niveauId);
            System.out.println("domaineId = " + domaineId);

            try {
                filiereClient.getFiliere(filiereId);
                niveauClient.getNiveau(niveauId);
                domaineClient.getDomaine(domaineId);
            } catch (Exception e) {
                throw new RuntimeException("Filière ou niveau ou domaine introuvable");
            }
// Sauvegarder l'étudiant
            Etudiant savedEtudiant = etudiantRepository.save(etudiant);

// Récupérer le User
            UserDto user = userClient.getUserById(savedEtudiant.getUserId());

// Contenu du QR Code
            String qrContent =
                    "Matricule : " + savedEtudiant.getMatricule() +
                            "\nEtudiant ID : " + savedEtudiant.getId();

// Génération du QR Code
            byte[] qrCode;
            try {
                qrCode = qrCodeService.generateQRCode(qrContent);
            } catch (Exception e) {
                throw new RuntimeException("Erreur lors de la génération du QR Code", e);
            }

// Préparer l'email
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(user.getEmail());
            emailRequest.setSubject("Bienvenue à l'université");
            emailRequest.setMessage(
                    "Bonjour " + user.getUsername() +
                            "\n\nVotre inscription est terminée." +
                            "\nVotre matricule est : " + savedEtudiant.getMatricule() +
                            "\n\nVeuillez trouver votre QR Code en pièce jointe."
            );

            emailRequest.setAttachment(qrCode);
            emailRequest.setFileName("QRCode_" + savedEtudiant.getMatricule() + ".png");

// Envoyer l'email
            emailClient.sendEmail(emailRequest);

            return savedEtudiant;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de créer ces fichiers");
        }

    }
    // GET ALL ETUDIANTS
    public Page<Etudiant> getAllEtudiants(Pageable pageable) {
        return etudiantRepository.findAll(pageable);
    }

    // GET BY ID
    public Etudiant getEtudiantById(Long id) {
        return etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etudiant introuvable"));
    }

    // GET BY MATRICULE
    public Etudiant getByMatricule(String matricule) {
        return etudiantRepository.findByMatricule(matricule)
                .orElseThrow(() -> new RuntimeException("Matricule introuvable"));
    }

    // UPDATE ETUDIANT
    public Etudiant updateEtudiant(Long id,
                                   String matricule,
                                   String cin,
                                   String adresse,
                                   String phone,
                                   TypeFormation typeFormation,
                                   MultipartFile photo,
                                   MultipartFile releve,
                                   MultipartFile diplome) {

        Etudiant existing = getEtudiantById(id);

        existing.setMatricule(matricule);
        existing.setCin(cin);
        existing.setAdresse(adresse);
        existing.setPhone(phone);
        if (typeFormation != null) {
            existing.setTypeFormation(typeFormation);
        }

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

            if (releve != null && !releve.isEmpty()) {
                if (existing.getReleve() != null) {
                    File old = new File(UPLOAD_DIR + existing.getReleve());
                    if (old.exists()) old.delete();

                    String releveName = UUID.randomUUID() + "_" + releve.getOriginalFilename();
                    Path path = Paths.get(UPLOAD_DIR, releveName);
                    Files.write(path, releve.getBytes());

                    existing.setReleve(releveName);
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

        return etudiantRepository.save(existing);
    }

    // DELETE ETUDIANT
    public void deleteEtudiant(Long id) {
        etudiantRepository.deleteById(id);
    }

    public Map<String,Object> getEtudiantComplet(Long id){

        Etudiant etudiant =
                getEtudiantById(id);


        UserDto user =
                userClient.getUserById(
                        etudiant.getUserId()
                );

        FiliereDto filiere =
                filiereClient.getFiliere(etudiant.getFiliereId());

        NiveauDto niveau =
                niveauClient.getNiveau(etudiant.getNiveauId());

        DomaineDto domaine =
                domaineClient.getDomaine(etudiant.getDomaineId());

        return Map.of(
                "etudiant", etudiant,
                "user", user,
                "filiere", filiere,
                "niveau", niveau,
                "domaine", domaine
        );
    }
}