package com.etec.matiere.service.impl;

import com.etec.matiere.client.SemestreClient;
import com.etec.matiere.dto.SemestreDto;
import com.etec.matiere.entity.Matiere;
import com.etec.matiere.repository.MatiereRepository;
import com.etec.matiere.service.MatiereService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MatiereServiceImpl implements MatiereService {

    private final MatiereRepository matiereRepository;
    private final SemestreClient semestreClient;
    @Override
    public Matiere save(Matiere matiere) {

        try{
            SemestreDto semestre =
                    semestreClient.getSemestre(
                            matiere.getSemestreId()
                    );


            if (semestre == null) {

                throw new RuntimeException(
                        "Semestre introuvable"
                );

            }

        }catch (FeignException e) {
            throw new RuntimeException("Semestre introuvable");
        }

        return matiereRepository.save(matiere);
    }

    @Override
    public Page<Matiere> findAll(Pageable pageable) {
        return matiereRepository.findAll(pageable);
    }

    @Override
    public Matiere findById(Long id) {
        return matiereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matière introuvable"));
    }

    @Override
    public Matiere update(Long id, Matiere matiere) {
        Matiere existing = findById(id);


        existing.setNom(matiere.getNom());
        existing.setCoefficient(matiere.getCoefficient());

        return matiereRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Matiere existing = findById(id);
        matiereRepository.delete(existing);

    }
}
