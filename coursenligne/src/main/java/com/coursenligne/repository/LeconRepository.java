package com.coursenligne.repository;

import com.coursenligne.entity.Lecon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LeconRepository extends JpaRepository<Lecon, Long> {

    Page<Lecon> findByChapitreId(Long chapitreId, Pageable pageable);

    // Récupérer les leçons d'un chapitre triées
    List<Lecon> findByChapitreIdOrderByIdAsc(Long chapitreId);

}
