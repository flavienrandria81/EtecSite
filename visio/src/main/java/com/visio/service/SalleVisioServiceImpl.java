package com.visio.service;

import com.common.common.dto.NotificationRequest;
import com.visio.client.*;
import com.visio.dto.*;
import com.visio.entity.*;
import com.visio.repository.SalleVisioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalleVisioServiceImpl implements SalleVisioService {

    private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    private final SalleVisioRepository salleVisioRepository;
    private final ParticipantVisioService participantService;

    private final EnseignantClient enseignantClient;
    private final MatiereClient matiereClient;
    private final CoursClient coursClient;
    private final EtudiantClient etudiantClient;
    private final NotificationClient notificationClient;
    private final PresenceClient presenceClient;

    @Override
    @Transactional
    public SalleVisioResponse creerSalle(SalleVisioRequest request, Long userId) {

        EnseignantDto enseignant = getEnseignantAuth(request.getEnseignantId(), userId);

        if (request.getMatiereId() != null) {
            matiereClient.getMatiereById(request.getMatiereId());
        }

        if (request.getCoursId() != null) {
            coursClient.getCoursById(request.getCoursId());
        }

        String code = genererCode();

        SalleVisio salle = SalleVisio.builder()
                .titre(request.getTitre())
                .description(request.getDescription())
                .enseignantId(enseignant.getId())
                .matiereId(request.getMatiereId())
                .coursId(request.getCoursId())
                .dateDebut(request.getDateDebut())
                .dateFin(request.getDateFin())
                .statut(StatutSalle.PLANIFIEE)
                .codeAcces(code)
                .lienConnexion("/visio/rejoindre/" + code)
                .createdAt(LocalDateTime.now())
                .build();

        SalleVisio saved = salleVisioRepository.save(salle);

        participantService.rejoindre(
                saved.getId(),
                ParticipantVisioRequest.builder()
                        .utilisateurId(userId)
                        .nom(enseignant.getMatricule() != null
                                ? "Enseignant " + enseignant.getMatricule()
                                : "Enseignant")
                        .role(RoleParticipant.ENSEIGNANT)
                        .build(),
                null);

        notifierEtudiants(request, saved);

        return toResponse(saved);
    }

    @Override
    @Transactional
    public SalleVisioResponse demarrerSalle(Long salleId, Long userId) {

        SalleVisio salle = getSalleEntity(salleId);
        verifierProprietaire(salle, userId);

        salle.setStatut(StatutSalle.EN_COURS);
        if (salle.getDateDebut() == null) {
            salle.setDateDebut(LocalDateTime.now());
        }
        SalleVisio saved = salleVisioRepository.save(salle);

        notifierRappelDemarrage(saved);

        return toResponse(saved);
    }

    @Override
    @Transactional
    public SalleVisioResponse terminerSalle(Long salleId, Long userId) {

        SalleVisio salle = getSalleEntity(salleId);
        verifierProprietaire(salle, userId);

        salle.setStatut(StatutSalle.TERMINEE);
        salle.setDateFin(LocalDateTime.now());
        SalleVisio saved = salleVisioRepository.save(salle);

        participantService.getParticipants(salleId)
                .forEach(p -> participantService.quitterParUtilisateur(salleId, p.getUtilisateurId()));

        return toResponse(saved);
    }

    @Override
    @Transactional
    public JoinSalleResponse rejoindreParCode(
            String codeAcces, Long userId, String nom, String role) {

        SalleVisio salle = salleVisioRepository
                .findByCodeAcces(codeAcces.toUpperCase())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Salle introuvable avec ce code"));

        if (salle.getStatut() == StatutSalle.TERMINEE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cette visioconférence est terminée");
        }

        RoleParticipant roleParticipant = "ENSEIGNANT".equalsIgnoreCase(role)
                ? RoleParticipant.ENSEIGNANT
                : RoleParticipant.ETUDIANT;

        String nomFinal = nom;
        Long etudiantId = null;

        if (roleParticipant == RoleParticipant.ETUDIANT) {
            EtudiantDto etudiant = verifierEtudiant(userId);
            nomFinal = etudiant.getPrenom() + " " + etudiant.getNom();
            etudiantId = etudiant.getId();

            verifierAffiliation(salle, etudiant);

        } else {
            verifierProprietaire(salle, userId);
        }

        ParticipantVisioResponse participant = participantService.rejoindre(
                salle.getId(),
                ParticipantVisioRequest.builder()
                        .utilisateurId(userId)
                        .nom(nomFinal)
                        .role(roleParticipant)
                        .build(),
                null);

        if (etudiantId != null) {
            enregistrerPresence(salle, etudiantId, userId);
        }

        return JoinSalleResponse.builder()
                .autorise(true)
                .message("Accès autorisé à la visioconférence")
                .salle(toResponse(salle))
                .participant(participant)
                .nom(nomFinal)
                .role(roleParticipant.name())
                .build();
    }

    @Override
    public void quitterParCode(String codeAcces, Long userId) {

        SalleVisio salle = salleVisioRepository
                .findByCodeAcces(codeAcces.toUpperCase())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Salle introuvable"));

        participantService.quitterParUtilisateur(salle.getId(), userId);
    }

    @Override
    public SalleVisioResponse getSalle(Long id) {
        return toResponse(getSalleEntity(id));
    }

    @Override
    public SalleVisioDetailResponse getDetail(Long id) {

        SalleVisio salle = getSalleEntity(id);

        EnseignantDto enseignant = null;
        MatiereDto matiere = null;
        CoursDto cours = null;

        try {
            if (salle.getEnseignantId() != null) {
                enseignant = enseignantClient.getEnseignantById(salle.getEnseignantId());
            }
            if (salle.getMatiereId() != null) {
                matiere = matiereClient.getMatiereById(salle.getMatiereId());
            }
            if (salle.getCoursId() != null) {
                cours = coursClient.getCoursById(salle.getCoursId());
            }
        } catch (Exception e) {
            log.warn("Récupération des détails échouée pour la salle {} : {}",
                    id, e.getMessage());
        }

        return SalleVisioDetailResponse.builder()
                .id(salle.getId())
                .titre(salle.getTitre())
                .description(salle.getDescription())
                .enseignantId(salle.getEnseignantId())
                .matiereId(salle.getMatiereId())
                .coursId(salle.getCoursId())
                .dateDebut(salle.getDateDebut())
                .dateFin(salle.getDateFin())
                .statut(salle.getStatut())
                .codeAcces(salle.getCodeAcces())
                .lienConnexion(salle.getLienConnexion())
                .enseignant(enseignant)
                .matiere(matiere)
                .cours(cours)
                .build();
    }

    @Override
    public SalleVisioResponse getSalleByCode(String codeAcces) {
        SalleVisio salle = salleVisioRepository
                .findByCodeAcces(codeAcces.toUpperCase())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Salle introuvable avec ce code"));
        return toResponse(salle);
    }

    @Override
    public List<SalleVisioResponse> getSalles() {
        return salleVisioRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalleVisioResponse> getSallesParEnseignant(Long enseignantId) {
        return salleVisioRepository
                .findByEnseignantIdOrderByDateDebutDesc(enseignantId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalleVisioResponse> getSallesParCours(Long coursId) {
        return salleVisioRepository
                .findByCoursIdOrderByDateDebutDesc(coursId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalleVisioResponse> getHistorique() {
        return salleVisioRepository
                .findByStatutOrderByDateDebutDesc(StatutSalle.TERMINEE)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalleVisioResponse> getSallesEnCours() {
        return salleVisioRepository
                .findByStatutOrderByDateDebutDesc(StatutSalle.EN_COURS)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private SalleVisio getSalleEntity(Long id) {
        return salleVisioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Salle introuvable avec l'id " + id));
    }

    private EnseignantDto getEnseignantAuth(Long enseignantId, Long userId) {
        EnseignantDto enseignant = enseignantClient.getEnseignantById(enseignantId);
        if (!enseignant.getUserId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Vous n'êtes pas autorisé à créer cette salle");
        }
        return enseignant;
    }

    private void verifierProprietaire(SalleVisio salle, Long userId) {
        try {
            EnseignantDto enseignant =
                    enseignantClient.getEnseignantById(salle.getEnseignantId());
            if (!enseignant.getUserId().equals(userId)) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Seul l'enseignant responsable peut gérer cette salle");
            }
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Impossible de vérifier l'enseignant responsable");
        }
    }

    private EtudiantDto verifierEtudiant(Long userId) {
        EtudiantDto etudiant = etudiantClient.getMonProfil();

        if (!"VALIDE".equalsIgnoreCase(etudiant.getStatut())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Votre compte étudiant doit être validé pour rejoindre la visioconférence");
        }

        if (etudiant.getUserId() == null || !etudiant.getUserId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Accès refusé");
        }
        return etudiant;
    }

    private void verifierAffiliation(SalleVisio salle, EtudiantDto etudiant) {
        if (salle.getCoursId() == null) {
            return;
        }
        try {
            CoursDto cours = coursClient.getCoursById(salle.getCoursId());
            boolean coursOk = (cours.getFiliereId() == null
                    || cours.getFiliereId().equals(etudiant.getFiliereId()))
                    && (cours.getNiveauId() == null
                    || cours.getNiveauId().equals(etudiant.getNiveauId()))
                    && (cours.getDomaineId() == null
                    || cours.getDomaineId().equals(etudiant.getDomaineId()));

            if (!coursOk) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Vous n'êtes pas inscrit au cours associé à cette visioconférence");
            }
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            log.warn("Vérification d'affiliation impossible, accès maintenu : {}", e.getMessage());
        }
    }

    private void enregistrerPresence(SalleVisio salle, Long etudiantId, Long userId) {
        try {
            presenceClient.enregistrerPresence(PresenceRequest.builder()
                    .etudiantId(etudiantId)
                    .userId(userId)
                    .datePresence(LocalDate.now())
                    .heurePresence(LocalTime.now())
                    .statut("PRESENT")
                    .remarque("Visio : " + salle.getTitre())
                    .build());
        } catch (Exception e) {
            log.warn("Enregistrement de présence impossible : {}", e.getMessage());
        }
    }

    private void notifierEtudiants(SalleVisioRequest request, SalleVisio salle) {
        if (request.getEtudiantIds() == null) {
            return;
        }
        for (Long etudiantId : request.getEtudiantIds()) {
            try {
                EtudiantDto etudiant = etudiantClient.getEtudiantById(etudiantId);
                notificationClient.envoyer(new NotificationRequest(
                        etudiant.getUserId(),
                        etudiantId,
                        "Nouvelle visioconférence",
                        "Vous êtes invité à la visioconférence « "
                                + salle.getTitre() + " ». Code d'accès : "
                                + salle.getCodeAcces()));
            } catch (Exception e) {
                log.warn("Notification impossible pour l'étudiant {} : {}",
                        etudiantId, e.getMessage());
            }
        }
    }

    private void notifierRappelDemarrage(SalleVisio salle) {
        participantService.getParticipants(salle.getId())
                .stream()
                .filter(p -> p.getRole() == RoleParticipant.ETUDIANT)
                .forEach(p -> {
                    try {
                        notificationClient.envoyer(new NotificationRequest(
                                p.getUtilisateurId(),
                                null,
                                "La visioconférence a démarré",
                                "La visioconférence « " + salle.getTitre()
                                        + " » vient de démarrer. Rejoignez avec le code : "
                                        + salle.getCodeAcces()));
                    } catch (Exception e) {
                        log.warn("Rappel impossible pour l'utilisateur {} : {}",
                                p.getUtilisateurId(), e.getMessage());
                    }
                });
    }

    private String genererCode() {
        SecureRandom random = new SecureRandom();
        for (int attempt = 0; attempt < 20; attempt++) {
            StringBuilder code = new StringBuilder(6);
            for (int i = 0; i < 6; i++) {
                code.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
            }
            String candidate = code.toString();
            if (salleVisioRepository.findByCodeAcces(candidate).isEmpty()) {
                return candidate;
            }
        }
        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Impossible de générer un code d'accès unique");
    }

    private SalleVisioResponse toResponse(SalleVisio s) {
        return SalleVisioResponse.builder()
                .id(s.getId())
                .titre(s.getTitre())
                .description(s.getDescription())
                .enseignantId(s.getEnseignantId())
                .matiereId(s.getMatiereId())
                .coursId(s.getCoursId())
                .dateDebut(s.getDateDebut())
                .dateFin(s.getDateFin())
                .statut(s.getStatut())
                .codeAcces(s.getCodeAcces())
                .lienConnexion(s.getLienConnexion())
                .createdAt(s.getCreatedAt())
                .build();
    }
}
