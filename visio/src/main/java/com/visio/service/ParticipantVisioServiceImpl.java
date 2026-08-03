package com.visio.service;

import com.visio.dto.ParticipantVisioRequest;
import com.visio.dto.ParticipantVisioResponse;
import com.visio.entity.ParticipantVisio;
import com.visio.repository.ParticipantVisioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantVisioServiceImpl implements ParticipantVisioService {

    private final ParticipantVisioRepository repository;

    @Override
    public ParticipantVisioResponse rejoindre(
            Long salleVisioId,
            ParticipantVisioRequest request,
            String sessionId) {

        List<ParticipantVisio> dejaPresent = repository
                .findBySalleVisioIdAndUtilisateurId(
                        salleVisioId, request.getUtilisateurId());

        if (!dejaPresent.isEmpty()) {
            ParticipantVisio existant = dejaPresent.get(0);
            existant.setNom(request.getNom());
            existant.setRole(request.getRole());
            existant.setSessionId(sessionId);
            existant.setActif(true);
            existant.setHeureConnexion(LocalDateTime.now());
            existant.setHeureDeconnexion(null);
            return toResponse(repository.save(existant));
        }

        ParticipantVisio participant = ParticipantVisio.builder()
                .salleVisioId(salleVisioId)
                .utilisateurId(request.getUtilisateurId())
                .nom(request.getNom())
                .role(request.getRole())
                .sessionId(sessionId)
                .actif(true)
                .heureConnexion(LocalDateTime.now())
                .build();

        return toResponse(repository.save(participant));
    }

    @Override
    public ParticipantVisioResponse quitter(Long salleVisioId, String sessionId) {

        ParticipantVisio participant = repository
                .findBySalleVisioIdAndSessionId(salleVisioId, sessionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Participant introuvable"));

        participant.setActif(false);
        participant.setHeureDeconnexion(LocalDateTime.now());

        return toResponse(repository.save(participant));
    }

    @Override
    public ParticipantVisioResponse quitterParUtilisateur(
            Long salleVisioId, Long utilisateurId) {

        List<ParticipantVisio> participants = repository
                .findBySalleVisioIdAndUtilisateurId(salleVisioId, utilisateurId);

        if (participants.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Participant introuvable");
        }

        ParticipantVisio participant = participants.get(0);
        participant.setActif(false);
        participant.setHeureDeconnexion(LocalDateTime.now());

        return toResponse(repository.save(participant));
    }

    @Override
    public List<ParticipantVisioResponse> getParticipants(Long salleVisioId) {
        return repository
                .findBySalleVisioIdOrderByHeureConnexionAsc(salleVisioId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public long countActifs(Long salleVisioId) {
        return repository.countBySalleVisioIdAndActifTrue(salleVisioId);
    }

    private ParticipantVisioResponse toResponse(ParticipantVisio p) {
        return ParticipantVisioResponse.builder()
                .id(p.getId())
                .salleVisioId(p.getSalleVisioId())
                .utilisateurId(p.getUtilisateurId())
                .nom(p.getNom())
                .role(p.getRole())
                .sessionId(p.getSessionId())
                .actif(p.isActif())
                .heureConnexion(p.getHeureConnexion())
                .heureDeconnexion(p.getHeureDeconnexion())
                .build();
    }
}
