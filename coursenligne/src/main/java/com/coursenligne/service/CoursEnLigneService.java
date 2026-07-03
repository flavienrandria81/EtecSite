package com.coursenligne.service;

import com.coursenligne.dto.CoursResponse;
import com.coursenligne.entity.CoursEnLigne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CoursEnLigneService {

    CoursEnLigne creerCours(CoursEnLigne cours);

    Page<CoursEnLigne> getAll(Pageable pageable);

    CoursEnLigne getById(Long id);

    void delete(Long id);

    CoursResponse getCoursById(Long id);
}
