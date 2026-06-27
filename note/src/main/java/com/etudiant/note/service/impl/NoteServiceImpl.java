package com.etudiant.note.service.impl;

import com.etudiant.note.Dto.EtudiantResponse;
import com.etudiant.note.Dto.MatiereDto;
import com.etudiant.note.client.EtudiantClient;
import com.etudiant.note.client.MatiereClient;
import com.etudiant.note.entity.Note;
import com.etudiant.note.repository.NoteRepository;
import com.etudiant.note.service.NoteService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final EtudiantClient etudiantClient;
    private final MatiereClient matiereClient;
    @Override
    public Note save(Note note) {

        try {
            EtudiantResponse etudiant = etudiantClient.getEtudiant(note.getEtudiantId());

            if (etudiant == null) {
                throw new RuntimeException("Etudiant Introuvable");
            }
        }catch (FeignException e) {

                System.out.println(
                        "Erreur Feign : "
                                + e.status()
                                + " "
                                + e.getMessage()
                );

                throw new RuntimeException(
                        "Etudiant introuvable"
                );
            }

        try{
            MatiereDto matiere =
                    matiereClient.getMatiere(
                            note.getMatiereId()
                    );


            if (matiere == null) {

                throw new RuntimeException(
                        "Matière introuvable"
                );

            }

            }catch (FeignException e) {
                throw new RuntimeException("Matiere introuvable");
            }

            return noteRepository.save(note);

    }
    @Override
    public Page<Note> findAll(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    @Override
    public Note findById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note introuvable"));
    }

    @Override
    public Note update(Long id, Note note) {
        Note existing = findById(id);

        existing.setValeur(note.getValeur());
        existing.setMention(note.getMention());
        return noteRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Note existing = findById(id);
        noteRepository.delete(existing);
    }

    @Override
    public List<Note> getByEtudiant(Long etudiantId) {
        return noteRepository.findByEtudiantId(etudiantId);
    }

    @Override
    public Double calculerMoyenne(Long etudiantId) {

        Double moyenne = noteRepository.calculerMoyenne(etudiantId);

        return moyenne != null ? moyenne : 0.0;
    }

    @Override
    public Map<String, Object> getNoteComplet(Long id) {


        Note note = findById(id);


        EtudiantResponse etudiant =
                etudiantClient.getEtudiant(
                        note.getEtudiantId()
                );


        MatiereDto matiere =
                matiereClient.getMatiere(
                        note.getMatiereId()
                );


      return Map.of(
                "etudiant", etudiant,
                "matiere", matiere
        );
    }
}
