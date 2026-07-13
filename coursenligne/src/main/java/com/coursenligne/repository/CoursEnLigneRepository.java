package com.coursenligne.repository;

import com.coursenligne.entity.CoursEnLigne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursEnLigneRepository extends JpaRepository<CoursEnLigne,Long> {
    Page<CoursEnLigne> findByActifTrue(Pageable pageable);
}