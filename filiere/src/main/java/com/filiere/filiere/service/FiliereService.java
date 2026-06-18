package com.filiere.filiere.service;

import com.filiere.filiere.entity.Filiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FiliereService {
    Filiere save(Filiere filiere);
    Page<Filiere> findAll(Pageable pageable);
    Filiere findById(Long id);
    Filiere update(Long id, Filiere filiere);
    void delete(Long id);
}
