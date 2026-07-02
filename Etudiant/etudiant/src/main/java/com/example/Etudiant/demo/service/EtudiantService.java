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

    // =========================
    // MAIN METHOD (REGISTER FULL)
    // =========================
    public Etudiant registerEtudiantComplete(
            EtudiantRegistrationDTO dto,
            MultipartFile photo,
            MultipartFile releve,
            MultipartFile diplome,
            HttpServletRequest request
    ) {
        try {

            // =========================
            // 1. VALIDATION
            // =========================
            if (etudiantRepository.existsByMatricule(dto.getMatricule())) {
                throw new RuntimeException("Matricule déjà utilisé");
            }

            if (photo == null || photo.isEmpty()
                    || releve == null || releve.isEmpty()
                    || diplome == null || diplome.isEmpty()) {
                throw new RuntimeException("Tous les fichiers sont obligatoires");
            }

            // =========================
            // 2. CREATE USER (MICROSERVICE)
            // =========================
            UserRegistrationDTO user = new UserRegistrationDTO();
            user.setUsername(dto.getUsername());
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword());
            user.setRole("ETUDIANT");

            UserDto savedUser = userClient.register(user);

            Long userId = savedUser.getId();

            // =========================
            // 3. GET USER FROM JWT (OPTIONAL CHECK)
            // =========================
            Long tokenUserId = (Long) request.getAttribute("userId");
            if (tokenUserId != null && !tokenUserId.equals(userId)) {
                throw new RuntimeException("Utilisateur invalide");
            }

            // =========================
            // 4. VERIFY EXTERNAL SERVICES
            // =========================
            filiereClient.getFiliere(dto.getFiliereId());
            niveauClient.getNiveau(dto.getNiveauId());
            domaineClient.getDomaine(dto.getDomaineId());

            // =========================
            // 5. UPLOAD FILES
            // =========================
            String photoName = saveFile(photo);
            String releveName = saveFile(releve);
            String diplomeName = saveFile(diplome);

            // =========================
            // 6. CREATE ETUDIANT
            // =========================
            Etudiant etudiant = new Etudiant();
            etudiant.setUserId(userId);
            etudiant.setMatricule(dto.getMatricule());
            etudiant.setCin(dto.getCin());
            etudiant.setPhone(dto.getTelephone());
            etudiant.setAdresse(dto.getAdresse());
            etudiant.setTypeFormation(dto.getTypeFormation());

            etudiant.setFiliereId(dto.getFiliereId());
            etudiant.setNiveauId(dto.getNiveauId());
            etudiant.setDomaineId(dto.getDomaineId());

            etudiant.setPhoto(photoName);
            etudiant.setReleve(releveName);
            etudiant.setDiplome(diplomeName);

            Etudiant saved = etudiantRepository.save(etudiant);

            // =========================
            // 7. QR CODE GENERATION
            // =========================
            String qrContent = "Matricule: " + saved.getMatricule()
                    + "\nID: " + saved.getId();

            byte[] qrCode = qrCodeService.generateQRCode(qrContent);
            saved.setQrCode(qrCode);

            etudiantRepository.save(saved);

            // =========================
            // 8. EMAIL NOTIFICATION
            // =========================
            UserDto userDto = userClient.getUserById(userId);

            EmailRequest email = new EmailRequest();
            email.setTo(userDto.getEmail());
            email.setSubject("Bienvenue à l'université");
            email.setMessage(
                    "Bonjour " + userDto.getUsername()
                            + "\nVotre inscription est terminée."
                            + "\nMatricule: " + saved.getMatricule()
            );

            email.setAttachment(qrCode);
            email.setFileName("QR_" + saved.getMatricule() + ".png");

            emailClient.sendEmail(email);

            return saved;

        } catch (Exception e) {
            throw new RuntimeException("Erreur inscription étudiant: " + e.getMessage(), e);
        }
    }

    // =========================
    // FILE UPLOAD METHOD
    // =========================
    private String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Fichier invalide");
        }

        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR, fileName);
        Files.write(path, file.getBytes());

        return fileName;
    }

    // =========================
    // READ METHODS
    // =========================
    public Page<Etudiant> getAllEtudiants(Pageable pageable) {
        return etudiantRepository.findAll(pageable);
    }

    public Etudiant getEtudiantById(Long id) {
        return etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etudiant introuvable"));
    }

    public Etudiant getByMatricule(String matricule) {
        return etudiantRepository.findByMatricule(matricule)
                .orElseThrow(() -> new RuntimeException("Matricule introuvable"));
    }

    // =========================
    // UPDATE ETUDIANT
    // =========================
    public Etudiant updateEtudiant(
            Long id,
            String matricule,
            String cin,
            String adresse,
            String phone,
            TypeFormation typeFormation,
            MultipartFile photo,
            MultipartFile releve,
            MultipartFile diplome
    ) {

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
                existing.setPhoto(saveFile(photo));
            }

            if (releve != null && !releve.isEmpty()) {
                existing.setReleve(saveFile(releve));
            }

            if (diplome != null && !diplome.isEmpty()) {
                existing.setDiplome(saveFile(diplome));
            }

        } catch (IOException e) {
            throw new RuntimeException("Erreur mise à jour fichiers");
        }

        return etudiantRepository.save(existing);
    }

    // =========================
    // DELETE ETUDIANT
    // =========================
    public void deleteEtudiant(Long id) {
        Etudiant e = getEtudiantById(id);
        etudiantRepository.delete(e);
    }

    // =========================
    // FULL DETAILS
    // =========================
    public Map<String, Object> getEtudiantComplet(Long id) {

        Etudiant etudiant = getEtudiantById(id);

        return Map.of(
                "etudiant", etudiant,
                "user", userClient.getUserById(etudiant.getUserId()),
                "filiere", filiereClient.getFiliere(etudiant.getFiliereId()),
                "niveau", niveauClient.getNiveau(etudiant.getNiveauId()),
                "domaine", domaineClient.getDomaine(etudiant.getDomaineId())
        );
    }
}