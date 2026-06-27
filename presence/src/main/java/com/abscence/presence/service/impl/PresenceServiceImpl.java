package com.abscence.presence.service.impl;

import com.abscence.presence.client.EmploiDuTempsClient;
import com.abscence.presence.client.EtudiantClient;
import com.abscence.presence.dto.EmploiDuTempsDto;
import com.abscence.presence.dto.EtudiantDto;
import com.abscence.presence.entity.Presence;
import com.abscence.presence.repository.PresenceRepository;
import com.abscence.presence.service.PresenceService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PresenceServiceImpl implements PresenceService {

    private final PresenceRepository repository;
    private final EtudiantClient etudiantClient;
    private final EmploiDuTempsClient emploiDuTempsClient;
    @Override
    public Presence save(Presence presence) {

        try{
            EtudiantDto etudiant =
                    etudiantClient.getEtudiant(
                            presence.getEtudiantId()
                    );


            if (etudiant == null) {

                throw new RuntimeException(
                        "Etudiant introuvable"
                );

            }

        }catch (FeignException e) {
            throw new RuntimeException("Etudiant introuvable");
        }

        try{
            EmploiDuTempsDto emploiDuTemps =
                    emploiDuTempsClient.getEmploiDuTemps(
                            presence.getEmploiDuTempsId()
                    );


            if (emploiDuTemps == null) {

                throw new RuntimeException(
                        "Emploi du temps introuvable"
                );

            }

        }catch (FeignException e) {
            throw new RuntimeException("Emploi du temps introuvable");
        }
        return repository.save(presence);
    }

    @Override
    public Page<Presence> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Presence finById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Presence introuvable"));
    }

    @Override
    public Presence update(Long id, Presence presence) {
        Presence existing = finById(id);

        existing.setStatut(presence.getStatut());
        existing.setRemarque(presence.getRemarque());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Presence existing = finById(id);
        repository.delete(existing);
    }
}
