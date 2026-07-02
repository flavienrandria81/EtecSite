package com.etec.actualite.service;

import com.etec.actualite.entity.Actuality;
import com.etec.actualite.entity.Categorie;
import com.etec.actualite.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ActualiteService {

    Actuality save(String titre, String description, Status status, Categorie categorie, MultipartFile file);
    Page<Actuality> findAll(Pageable pageable);
    Actuality findById(Long id);
    Actuality update(Long id, String titre, String description, Status status, Categorie categorie, MultipartFile file);
    String delete(Long id);

}