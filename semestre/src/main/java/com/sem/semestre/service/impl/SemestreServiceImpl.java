package com.sem.semestre.service.impl;

import com.sem.semestre.client.UniversitaireClient;
import com.sem.semestre.dto.AnneesUnivDto;
import com.sem.semestre.entity.Semestre;
import com.sem.semestre.repository.SemestreRepository;
import com.sem.semestre.service.SemestreService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SemestreServiceImpl implements SemestreService {

    private final SemestreRepository repository;
    private final UniversitaireClient universitaireClient;
    @Override
    public Semestre save(Semestre semestre) {

        boolean existe = repository
                .existsByNumeroAndAnneesUnivId(
                        semestre.getNumero(),
                        semestre.getAnneesUnivId()
                );


        if (existe) {
            throw new RuntimeException(
                    "Ce semestre existe déjà pour cette année universitaire"
            );
        }

        semestre.setActif(false);

        try{
            AnneesUnivDto annees =
                    universitaireClient.getAnneesUniv(
                            semestre.getAnneesUnivId()
                    );


            if (annees == null) {

                throw new RuntimeException(
                        "Années Universitaire introuvable"
                );

            }

        }catch (FeignException e) {
            throw new RuntimeException("Années Universitaire introuvable");
        }

        return repository.save(semestre);
    }

@Override
    public Page<Semestre> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Semestre findById(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Semestre introuvable"));
    }

    @Override
    public Semestre update(Long id, Semestre semestre) {
        Semestre existing = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Semestre introuvable"));

        existing.setNom(semestre.getNom());
        existing.setNumero(semestre.getNumero());
        existing.setDateDebut(semestre.getDateDebut());
        existing.setDateFin(semestre.getDateFin());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Semestre existing = findById(id);
        repository.delete(existing);

    }

    @Override
    public Semestre activer(Long id) {

        repository.findAll().forEach(s -> {
            s.setActif(false);
            repository.save(s);
        });

        Semestre semestre = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Semestre introuvable"));

        semestre.setActif(true);

        return repository.save(semestre);
    }

    @Override
    public Semestre semestreActif() {

        return repository.findByActifTrue()
                .orElseThrow(() -> new RuntimeException("Aucun semestre actif"));
    }

}
