package com.moyenne.service.impl;

import com.common.common.dto.NotificationRequest;
import com.moyenne.client.NoteClient;
import com.moyenne.client.NotificationClient;
import com.moyenne.dto.NoteDto;
import com.moyenne.entity.Moyenne;
import com.moyenne.repository.MoyenneRepository;
import com.moyenne.service.MoyenneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MoyenneServiceImpl implements MoyenneService {

    private final MoyenneRepository moyenneRepository;
    private final NoteClient noteClient;
    private final NotificationClient notificationClient;

    @Override
    public Moyenne calculerMoyenne(Long etudiantId) {

        List<NoteDto> notes = noteClient.getNotes(etudiantId);

        if (notes.isEmpty()) {
            throw new RuntimeException("Aucune note trouvée pour cet étudiant.");
        }

        double somme = 0;

        for (NoteDto note : notes) {
            somme += note.getValeur();
        }

        double moyenne = somme / notes.size();

        Moyenne resultat = moyenneRepository
                .findByEtudiantId(etudiantId)
                .orElse(new Moyenne());

        resultat.setEtudiantId(etudiantId);
        resultat.setMoyenneGenerale(moyenne);
        resultat.setMention(calculMention(moyenne));


        Moyenne saved = moyenneRepository.save(resultat);

// Création de la notification
        NotificationRequest request = new NotificationRequest();

        request.setEtudiant(saved.getEtudiantId());
        request.setUserId(notes.get(0).getUserId());

        request.setTitre("Moyenne calculée");

        request.setMessage(
                "Votre moyenne générale est de "
                        + saved.getMoyenneGenerale()
                        + " (" + saved.getMention() + ")."
        );

        notificationClient.envoyer(request);

        return saved;
    }
    
    @Override
    public Page<Moyenne> findAll(Pageable pageable) {
        return moyenneRepository.findAll(pageable);
    }

    @Override
    public Moyenne findById(Long id) {
        return moyenneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moyenne introuvable"));
    }

    @Override
    public Moyenne findByEtudiantId(Long etudiantId) {
        return moyenneRepository.findByEtudiantId(etudiantId)
                .orElseThrow(() -> new RuntimeException("Moyenne introuvable"));
    }

    @Override
    public void delete(Long id) {
        Moyenne moyenne = findById(id);
        moyenneRepository.delete(moyenne);
    }

    private String calculMention(double moyenne) {

        if (moyenne >= 16) {
            return "Très Bien";
        } else if (moyenne >= 14) {
            return "Bien";
        } else if (moyenne >= 12) {
            return "Assez Bien";
        } else if (moyenne >= 10) {
            return "Passable";
        } else {
            return "Ajourné";
        }
    }
}