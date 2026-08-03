package com.visio.service;

import com.visio.dto.ParticipantVisioRequest;
import com.visio.dto.ParticipantVisioResponse;

import java.util.List;

public interface ParticipantVisioService {

    ParticipantVisioResponse rejoindre(
            Long salleVisioId,
            ParticipantVisioRequest request,
            String sessionId);

    ParticipantVisioResponse quitter(Long salleVisioId, String sessionId);

    ParticipantVisioResponse quitterParUtilisateur(Long salleVisioId, Long utilisateurId);

    List<ParticipantVisioResponse> getParticipants(Long salleVisioId);

    long countActifs(Long salleVisioId);
}
