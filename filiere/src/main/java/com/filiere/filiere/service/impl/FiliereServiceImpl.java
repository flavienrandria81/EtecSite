package com.filiere.filiere.service.impl;

import com.filiere.filiere.client.NiveauClient;
import com.filiere.filiere.dto.NiveauDTO;
import com.filiere.filiere.entity.Filiere;
import com.filiere.filiere.repository.FiliereRepository;
import com.filiere.filiere.service.FiliereService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FiliereServiceImpl implements FiliereService {

    private final FiliereRepository repository;
    private final NiveauClient niveauClient;
    @Override
    public Filiere save(Filiere filiere) {
        try{
            NiveauDTO niveau =
                    niveauClient.getNiveau(
                            filiere.getNiveauId()
                    );


            if (niveau == null) {

                throw new RuntimeException(
                        "Niveau introuvable"
                );

            }

        }catch (FeignException e) {
            throw new RuntimeException("Niveau introuvable");
        }

        return repository.save(filiere);
    }

    @Override
    public Page<Filiere> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Filiere findById(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Filiere introuvable"));
    }

    @Override
    public Filiere update(Long id, Filiere filiere) {
        Filiere existing = findById(id);

        existing.setNom(filiere.getNom());
        existing.setCode(filiere.getCode());
        existing.setDescription(filiere.getDescription());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Filiere existing = findById(id);

        repository.delete(existing);
    }
}
