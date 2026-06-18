package com.niveau.niveau.service.impl;

import com.niveau.niveau.entity.Niveau;
import com.niveau.niveau.repository.NiveauRepository;
import com.niveau.niveau.service.NiveauService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NiveauServiceImpl implements NiveauService {

    private final NiveauRepository repository;
    @Override
    public Niveau save(Niveau niveau) {
        return repository.save(niveau);
    }

    @Override
    public Page<Niveau> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Niveau findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Niveau introuvable"));
    }

    @Override
    public Niveau update(Long id, Niveau niveau) {
        Niveau existing = findById(id);

        existing.setNom(niveau.getNom());
        existing.setDescription(niveau.getDescription());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Niveau existing = findById(id);
        repository.delete(existing);

    }
}
