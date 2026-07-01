package com.etudiant.note.service.impl;

import com.common.common.dto.NotificationRequest;
import com.etudiant.note.Dto.*;
import com.etudiant.note.client.*;
import com.etudiant.note.entity.Note;
import com.etudiant.note.repository.NoteRepository;
import com.etudiant.note.service.NoteService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final EtudiantClient etudiantClient;
    private final MatiereClient matiereClient;
    private final NotificationClient notificationClient;
    private final UserClient userClient;
    private final FiliereClient filiereClient;
    private final NiveauClient niveauClient;
    private final DomaineClient domaineClient;
    private final EnseignantClient enseignantClient;
    private final SemestreClient semestreClient;
    private final AnneesUnivClient anneesUnivClient;
    @Override
    public Note save(Note note) {

        EtudiantResponse etudiant;
        try {
          etudiant = etudiantClient.getEtudiant(note.getEtudiantId());

            if (etudiant == null) {
                throw new RuntimeException("Etudiant Introuvable");
            }
        }catch (FeignException e) {
        throw new RuntimeException("Etudiant introuvable");
    }

        try {
            System.out.println("UserId = " + note.getUserId());
            UserDto user =
                    userClient.getUser(
                            note.getUserId()
                    );


            if (user == null) {

                throw new RuntimeException(
                        "Utilisateur introuvable"
                );

            }

        }catch (FeignException e) {

                System.out.println("============== FEIGN USER ==============");
                System.out.println("Status : " + e.status());
                System.out.println("Message : " + e.getMessage());
                e.printStackTrace();

                throw e;
            }
        try{
            FiliereDto filiere =
                    filiereClient.getFiliere(
                            note.getFiliereId()
                    );


            if (filiere == null) {

                throw new RuntimeException(
                        "Filière introuvable"
                );

            }

        }catch (FeignException e) {

            System.out.println("============== FEIGN FILIERE ==============");
            System.out.println("Status : " + e.status());
            System.out.println("Message : " + e.getMessage());
            e.printStackTrace();

            throw e;
        }

        try{
            NiveauDto niveau =
                    niveauClient.getNiveau(
                            note.getNiveauId()
                    );


            if (niveau == null) {

                throw new RuntimeException(
                        "Niveau introuvable"
                );

            }

        }catch (FeignException e) {
            throw new RuntimeException("Niveau introuvable");
        }

        try{
            DomaineDto domaine =
                    domaineClient.getDomaine(
                            note.getDomaineId()
                    );


            if (domaine == null) {

                throw new RuntimeException(
                        "Domaine introuvable"
                );

            }

        }catch (FeignException e) {
            throw new RuntimeException("Domaine introuvable");
        }

        try{
            EnseignantDto enseignant =
                    enseignantClient.getEnseignant(
                            note.getEnseignantId()
                    );


            if (enseignant == null) {

                throw new RuntimeException(
                        "Enseignant introuvable"
                );

            }

        }catch (FeignException e) {
            throw new RuntimeException("Enseignant introuvable");
        }

        try{
            SemestreDto semestre =
                    semestreClient.getSemestre(
                            note.getSemestreId()
                    );


            if (semestre == null) {

                throw new RuntimeException(
                        "Semestre introuvable"
                );

            }

        }catch (FeignException e) {
            throw new RuntimeException("Semestre introuvable");
        }

        try{
            AnneesUnivDto annees =
                    anneesUnivClient.getAnnees(
                            note.getAnneesUnivId()
                    );


            if (annees == null) {

                throw new RuntimeException(
                        "Années Universitaire introuvable"
                );

            }

        }catch (FeignException e) {
            throw new RuntimeException("Années Universitaire introuvable");
        }

        try{
            MatiereDto matiere =
                    matiereClient.getMatiere(
                            note.getMatiereId()
                    );


            if (matiere == null) {

                throw new RuntimeException(
                        "Matière introuvable"
                );

            }

        }catch (FeignException e) {
            throw new RuntimeException(
                    "Matiere introuvable"
            );
        }

        Note saved = noteRepository.save(note);

        // Création de la notification
        NotificationRequest request = new NotificationRequest();

        request.setUserId(saved.getUserId());
        request.setEtudiantId(etudiant.getId());

        request.setTitre("Nouvelle note");

        request.setMessage(
                "Votre note de "
                        + saved.getValeur()
                        + " a été enregistrée."
        );

        // Envoi de la notification
        notificationClient.envoyer(request);

        return saved;
    }

    @Override
    public Page<Note> findAll(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    @Override
    public Note findById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note introuvable"));
    }

    @Override
    public Note update(Long id, Note note) {
        Note existing = findById(id);

        existing.setValeur(note.getValeur());
        existing.setMention(note.getMention());
        return noteRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Note existing = findById(id);
        noteRepository.delete(existing);
    }

    @Override
    public List<Note> getByEtudiant(Long etudiantId) {
        return noteRepository.findByEtudiantId(etudiantId);
    }

    @Override
    public Map<String, Object> getNoteComplet(Long id) {


        Note note = findById(id);


        EtudiantResponse etudiant =
                etudiantClient.getEtudiant(
                        note.getEtudiantId()
                );


        UserDto user =
                userClient.getUser(
                        note.getUserId()
                );

        FiliereDto filiere =
                filiereClient.getFiliere(
                        note.getFiliereId()
                );

        NiveauDto niveau =
                niveauClient.getNiveau(
                        note.getNiveauId()
                );

        DomaineDto domaine =
                domaineClient.getDomaine(
                        note.getDomaineId()
                );


        MatiereDto matiere =
                matiereClient.getMatiere(
                        note.getMatiereId()
                );


      return Map.of(
                "etudiant", etudiant,
                "user", user,
                "filiere", filiere,
                "niveau", niveau,
                "domaine", domaine,
                "matiere", matiere
        );
    }
}
