package com.coursenligne.service.impl;

import com.coursenligne.client.DomaineClient;
import com.coursenligne.client.EnseignantClient;
import com.coursenligne.client.FiliereClient;
import com.coursenligne.client.MatiereClient;
import com.coursenligne.client.NiveauClient;
import com.coursenligne.dto.CoursResponse;
import com.coursenligne.entity.CoursEnLigne;
import com.coursenligne.repository.CoursEnLigneRepository;
import com.coursenligne.service.CoursEnLigneService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoursEnLigneServiceImpl implements CoursEnLigneService {

    private final CoursEnLigneRepository repository;

    private final MatiereClient matiereClient;
    private final EnseignantClient enseignantClient;
    private final FiliereClient filiereClient;
    private final NiveauClient niveauClient;
    private final DomaineClient domaineClient;

    @Override
    public CoursEnLigne creerCours(CoursEnLigne cours) {
        cours.setId(null);
        return repository.save(cours);
    }

    @Override
    public Page<CoursEnLigne> getAll(Pageable pageable) {
        return repository.findByActifTrue(pageable);
    }

    @Override
    public CoursEnLigne getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cours introuvable avec l'id : " + id));
    }

    @Override
    public void delete(Long id) {
        CoursEnLigne cours = getById(id);
        repository.delete(cours);
    }

    @Override
    public CoursResponse getCoursById(Long id) {

        CoursEnLigne cours = getById(id);

        CoursResponse response = new CoursResponse();

        response.setId(cours.getId());
        response.setTitre(cours.getTitre());
        response.setDescription(cours.getDescription());

        response.setMatiere(
                matiereClient.getMatiereById(cours.getMatiereId())
        );

        response.setEnseignant(
                enseignantClient.getEnseignantById(cours.getEnseignantId())
        );

        response.setFiliere(
                filiereClient.getFiliereById(cours.getFiliereId())
        );

        response.setNiveau(
                niveauClient.getNiveauById(cours.getNiveauId())
        );

        response.setDomaine(
                domaineClient.getDomaineById(cours.getDomaineId())
        );

        return response;
    }

}