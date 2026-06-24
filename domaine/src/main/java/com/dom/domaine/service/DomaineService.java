package com.dom.domaine.service;

import com.dom.domaine.entity.Domaine;
import com.dom.domaine.repository.DomaineRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DomaineService {

    private final DomaineRepositoy domaineRepositoy;


    public Domaine save(Domaine domaine) {
        return domaineRepositoy.save(domaine);
    }

    public Page<Domaine> findAll(Pageable pageable) {
        return domaineRepositoy.findAll(pageable);
    }

    public Domaine findById(Long id) {
        return domaineRepositoy.findById(id)
                .orElseThrow(()-> new RuntimeException("Domaine Introuvable"));
    }


    public Domaine update(Long id, Domaine domaine) {
        Domaine existing = findById(id);

        existing.setNom(domaine.getNom());
        existing.setDescription(domaine.getDescription());
        return domaineRepositoy.save(existing);
    }


    public void delete(Long id) {
        Domaine existing = findById(id);
        domaineRepositoy.delete(existing);
    }
}
