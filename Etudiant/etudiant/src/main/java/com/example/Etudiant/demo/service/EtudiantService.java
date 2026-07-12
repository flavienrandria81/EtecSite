package com.example.Etudiant.demo.service;

import com.example.Etudiant.demo.client.*;
import com.example.Etudiant.demo.dto.*;
import com.example.Etudiant.demo.entity.Etudiant;
import com.example.Etudiant.demo.entity.StatutEtudiant;
import com.example.Etudiant.demo.entity.TypeFormation;
import com.example.Etudiant.demo.repository.EtudiantRepository;

import jakarta.transaction.Transactional;
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
    private static final String UPLOAD_DIR = "uploads/";


    @Transactional
    public Etudiant registerEtudiantComplete(
            EtudiantRegistrationDTO dto,
            MultipartFile photo,
            MultipartFile releve,
            MultipartFile diplome
    ) {

        if(etudiantRepository.existsByMatricule(dto.getMatricule())){

            throw new RuntimeException(
                    "Matricule déjà utilisé"
            );
        }

        if(photo == null || photo.isEmpty()
                || releve == null || releve.isEmpty()
                || diplome == null || diplome.isEmpty()){

            throw new RuntimeException(
                    "Tous les fichiers sont obligatoires"
            );
        }

        // Vérification données externes

        filiereClient.getFiliere(dto.getFiliereId());
        niveauClient.getNiveau(dto.getNiveauId());
        domaineClient.getDomaine(dto.getDomaineId());

        // Création User

        UserRegistrationDTO user =
                new UserRegistrationDTO();

        user.setUsername(dto.getUsername());
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole("ETUDIANT");

        UserDto savedUser = userClient.register(user);

        try {

            Etudiant etudiant = new Etudiant();

            etudiant.setUserId(savedUser.getId());
            etudiant.setMatricule(dto.getMatricule());
            etudiant.setCin(dto.getCin());
            etudiant.setAdresse(dto.getAdresse());
            etudiant.setPhone(dto.getTelephone());
            etudiant.setTypeFormation(dto.getTypeFormation());
            etudiant.setFiliereId(dto.getFiliereId());
            etudiant.setNiveauId(dto.getNiveauId());
            etudiant.setDomaineId(dto.getDomaineId());
            etudiant.setPhoto(saveFile(photo));
            etudiant.setReleve(saveFile(releve));
            etudiant.setDiplome(saveFile(diplome));

            // attente admin

            etudiant.setStatut(StatutEtudiant.EN_ATTENTE);

            return etudiantRepository.save(etudiant);



        } catch(IOException e){

            throw new RuntimeException(
                    "Erreur upload fichier"
            );
        }

    }


    @Transactional
    public Etudiant validerEtudiant(Long id){

        Etudiant etudiant = getEtudiantById(id);


        UserDto user = userClient.getUserById(
                        etudiant.getUserId()
                );

        FiliereDto filiere = filiereClient.getFiliere(
                        etudiant.getFiliereId()
                );

        NiveauDto niveau = niveauClient.getNiveau(
                        etudiant.getNiveauId()
                );

        DomaineDto domaine = domaineClient.getDomaine(
                        etudiant.getDomaineId()
                );

        String contenu =

                "Matricule : " + etudiant.getMatricule()

                        + "\nNom : " + user.getNom()

                        + "\nPrenom : " + user.getPrenom()

                        + "\nFiliere : " + filiere.getNom()

                        + "\nNiveau : " + niveau.getNom()

                        + "\nDomaine : " + domaine.getNom()

                        + "\nType Formation : " + etudiant.getTypeFormation();


        try {
            byte[] qr =
                    qrCodeService.generateQRCode(contenu);

            etudiant.setQrCode(qr);


            etudiant.setStatut(StatutEtudiant.VALIDE);


            EmailRequest email = new EmailRequest();

            email.setTo(user.getEmail());

            email.setSubject("Validation étudiant");

            email.setMessage("Votre inscription est validée");

            email.setAttachment(qr);

            email.setFileName("QR_"+etudiant.getMatricule()+".png");

            emailClient.sendEmail(email);

            return etudiantRepository.save(etudiant);

        }catch(Exception e){

            throw new RuntimeException("Erreur QR Code : " + e.getMessage());

        }

    }


    @Transactional
    public Etudiant refuserEtudiant(Long id){

        Etudiant etudiant = getEtudiantById(id);

        etudiant.setStatut(StatutEtudiant.REFUSE);

        return etudiantRepository.save(etudiant);

    }


    @Transactional
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
    ){


        Etudiant etudiant = getEtudiantById(id);

        etudiant.setMatricule(matricule);
        etudiant.setCin(cin);
        etudiant.setAdresse(adresse);
        etudiant.setPhone(phone);

        if(typeFormation != null){
            etudiant.setTypeFormation(typeFormation);
        }



        try {

            if(photo != null && !photo.isEmpty()){

                etudiant.setPhoto(saveFile(photo));

            }

            if(releve != null && !releve.isEmpty()){

                etudiant.setReleve(saveFile(releve));
            }

            if(diplome != null && !diplome.isEmpty()){

                etudiant.setDiplome(saveFile(diplome));

            }

        }catch(IOException e){
            throw new RuntimeException("Erreur fichier");
        }



        return etudiantRepository.save(etudiant);

    }


    @Transactional
    public void deleteEtudiant(Long id){

        Etudiant e =
                getEtudiantById(id);


        etudiantRepository.delete(e);

    }



    private String saveFile(MultipartFile file)
            throws IOException {

        File dir = new File(UPLOAD_DIR);



        if(!dir.exists()){
            dir.mkdirs();
        }


        String filename = UUID.randomUUID() +"_" +file.getOriginalFilename();

        Path path = Paths.get(UPLOAD_DIR, filename);
        Files.write(path, file.getBytes());


        return filename;

    }

    public Page<Etudiant> getAllEtudiants(Pageable pageable){

        return etudiantRepository.findAll(pageable);

    }

    public Etudiant getEtudiantById(Long id){


        return etudiantRepository.findById(id)

                .orElseThrow(
                        () -> new RuntimeException(
                                "Etudiant introuvable"
                        )
                );

    }


    public Etudiant getByMatricule(String matricule){


        return etudiantRepository
                .findByMatricule(matricule)

                .orElseThrow(
                        () -> new RuntimeException(
                                "Matricule introuvable"
                        )
                );

    }

    public Map<String,Object> getEtudiantComplet(Long id){


        Etudiant e =
                getEtudiantById(id);



        return Map.of(

                "etudiant", e,

                "user",
                userClient.getUserById(
                        e.getUserId()
                ),

                "filiere",
                filiereClient.getFiliere(
                        e.getFiliereId()
                ),

                "niveau",
                niveauClient.getNiveau(
                        e.getNiveauId()
                ),

                "domaine",
                domaineClient.getDomaine(
                        e.getDomaineId()
                )
        );

    }

    public Etudiant getByUserId(Long userId) {

        return etudiantRepository
                .findByUserId(userId)

                .orElseThrow(
                        () -> new RuntimeException(
                                "Etudiant introuvable pour cet utilisateur"
                        )
                );

    }


}