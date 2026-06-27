package com.sem.semestre.repository;

import com.sem.semestre.entity.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SemestreRepository extends JpaRepository<Semestre, Long> {
    Optional<Semestre> findByActifTrue();
    boolean existsByNumeroAndAnneesUnivId(Integer numero, Long anneesUnivId);

}
