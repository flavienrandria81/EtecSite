package com.coursenligne.repository;

import com.coursenligne.entity.CoursEnLigne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursEnLigneRepository extends JpaRepository<CoursEnLigne, Long> {

    Page<CoursEnLigne> findByMatiereId(Long matiereId, Pageable pageable);
}
