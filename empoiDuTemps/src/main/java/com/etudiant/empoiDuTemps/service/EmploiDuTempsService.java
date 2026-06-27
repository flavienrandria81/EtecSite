package com.etudiant.empoiDuTemps.service;

import com.etudiant.empoiDuTemps.entity.EmploiDuTemps;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface EmploiDuTempsService {

    EmploiDuTemps save(EmploiDuTemps emploiDuTemps);
    Page<EmploiDuTemps> findAll(Pageable pageable);
    EmploiDuTemps finById(Long id);
    EmploiDuTemps update(Long id, EmploiDuTemps emploiDuTemps);
    void delete(Long id);
    Map<String, Object> getNoteComplet(Long id);
}
