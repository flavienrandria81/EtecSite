package com.visio.repository;

import com.visio.entity.SalleVisio;
import com.visio.entity.StatutSalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SalleVisioRepository extends JpaRepository<SalleVisio, Long> {

    Optional<SalleVisio> findByCodeAcces(String codeAcces);

    List<SalleVisio> findByEnseignantIdOrderByDateDebutDesc(Long enseignantId);

    List<SalleVisio> findByStatutOrderByDateDebutDesc(StatutSalle statut);

    List<SalleVisio> findByCoursIdOrderByDateDebutDesc(Long coursId);

    List<SalleVisio> findByMatiereIdOrderByDateDebutDesc(Long matiereId);

    List<SalleVisio> findByDateDebutBetweenOrderByDateDebutAsc(
            LocalDateTime debut, LocalDateTime fin);
}
