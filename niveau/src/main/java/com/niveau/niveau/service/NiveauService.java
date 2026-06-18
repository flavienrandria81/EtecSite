package com.niveau.niveau.service;

import com.niveau.niveau.entity.Niveau;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NiveauService {

    Niveau save(Niveau niveau);
    Page<Niveau> findAll(Pageable pageable);
    Niveau findById(Long id);

    Niveau update(Long id, Niveau niveau);
    void delete(Long id);
}
