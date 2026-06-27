package com.etudiant.empoiDuTemps.service.impl;

import com.etudiant.empoiDuTemps.client.FiliereClient;
import com.etudiant.empoiDuTemps.client.MatiereClient;
import com.etudiant.empoiDuTemps.dto.FiliereDto;
import com.etudiant.empoiDuTemps.dto.MatiereDto;
import com.etudiant.empoiDuTemps.entity.EmploiDuTemps;
import com.etudiant.empoiDuTemps.repository.EmploiDuTempsRepository;
import com.etudiant.empoiDuTemps.service.EmploiDuTempsService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmploiDuTempsServiceImpl implements EmploiDuTempsService {

    private final EmploiDuTempsRepository repository;
    private final MatiereClient matiereClient;
    private final FiliereClient filiereClient;

    @Override
    public EmploiDuTemps save(EmploiDuTemps emploiDuTemps) {

        System.out.println("Jours = " + emploiDuTemps.getJours());
        System.out.println("Année = " + emploiDuTemps.getAnneesUniversitaire());
        System.out.println("Objet reçu : " + emploiDuTemps);

        try {
            MatiereDto matiere = matiereClient.getMiatiere(emploiDuTemps.getMatiereId());

            if (matiere == null) {
                throw new RuntimeException("Matiere Introuvable");
            }
        }catch (FeignException e){
            throw new RuntimeException("Matière introuvable");
        }

        try {
            FiliereDto filiere = filiereClient.getFiliere(emploiDuTemps.getFiliereId());

            if (filiere == null) {
                throw new RuntimeException("Filière Introuvable");
            }
        }catch (FeignException e) {
            throw new RuntimeException("Matiere Introuvable");
        }

        return repository.save(emploiDuTemps);
    }

    @Override
    public Page<EmploiDuTemps> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public EmploiDuTemps finById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emploi du temps introuvable"));
    }

    @Override
    public EmploiDuTemps update(Long id, EmploiDuTemps emploiDuTemps) {
        EmploiDuTemps existing = finById(id);

        existing.setDate(emploiDuTemps.getDate());
        existing.setHeureDebut(emploiDuTemps.getHeureDebut());
        existing.setHeureFin(emploiDuTemps.getHeureFin());
        existing.setAnneesUniversitaire(emploiDuTemps.getAnneesUniversitaire());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Map<String, Object> getNoteComplet(Long id) {
        EmploiDuTemps emploiDuTemps = finById(id);


        FiliereDto filiere =
                filiereClient.getFiliere(
                        emploiDuTemps.getFiliereId()
                );


        MatiereDto matiere =
                matiereClient.getMiatiere(
                        emploiDuTemps.getMatiereId()
                );


        return Map.of(
                "filiere", filiere,
                "matiere", matiere
        );
    }
}
