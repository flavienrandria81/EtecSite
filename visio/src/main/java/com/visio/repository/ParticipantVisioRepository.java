package com.visio.repository;

import com.visio.entity.ParticipantVisio;
import com.visio.entity.RoleParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantVisioRepository extends JpaRepository<ParticipantVisio, Long> {

    List<ParticipantVisio> findBySalleVisioIdOrderByHeureConnexionAsc(Long salleVisioId);

    List<ParticipantVisio> findBySalleVisioIdAndActifTrueOrderByHeureConnexionAsc(
            Long salleVisioId);

    Optional<ParticipantVisio> findBySessionId(String sessionId);

    Optional<ParticipantVisio> findBySalleVisioIdAndSessionId(Long salleVisioId, String sessionId);

    List<ParticipantVisio> findBySalleVisioIdAndRoleOrderByHeureConnexionAsc(
            Long salleVisioId, RoleParticipant role);

    List<ParticipantVisio> findBySalleVisioIdAndUtilisateurId(Long salleVisioId, Long utilisateurId);

    long countBySalleVisioIdAndActifTrue(Long salleVisioId);
}
