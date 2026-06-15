package com.example.Enseignant.demo.Service;

import com.example.Enseignant.demo.Entity.Enseignant;
import com.example.Enseignant.demo.Repository.EnseignantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;

    // ── Tout récupérer ────────────────────────────────────────────────────────
    public Page<Enseignant> getAllEnseignants(Pageable pageable) {
        return enseignantRepository.findAll(pageable);
    }

    // ── Récupérer par ID ──────────────────────────────────────────────────────
    public Optional<Enseignant> getEnseignantById(Long id) {
        return enseignantRepository.findById(id);
    }

    // ── Récupérer par Mail ─────────────────────────────────────────────────────
    public Optional<Enseignant> getEnseignantByMail(String mail) {
        return enseignantRepository.findByMail(mail);
    }

    // ── Créer un enseignant (POST) ────────────────────────────────────────────
    @Transactional
    public Enseignant createEnseignant(Enseignant enseignant) {
        if (enseignantRepository.existsByMail(enseignant.getMail())) {
            throw new RuntimeException("Un enseignant avec cet email existe déjà.");
        }
        return enseignantRepository.save(enseignant);
    }

    // ── Modifier un enseignant (PUT) ──────────────────────────────────────────
    @Transactional
    public Enseignant updateEnseignant(Long id, Enseignant updated) {
        Enseignant existing = enseignantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable avec l'ID : " + id));

        // Sécurité mail unique
        if (!existing.getMail().equals(updated.getMail()) && enseignantRepository.existsByMail(updated.getMail())) {
            throw new RuntimeException("Cet email est déjà utilisé par un autre enseignant.");
        }

        // Mise à jour de tous les champs de ton entité
        existing.setNom(updated.getNom());
        existing.setPrenom(updated.getPrenom());
        existing.setCours(updated.getCours());
        existing.setPhoto(updated.getPhoto());
        existing.setAdresse(updated.getAdresse());
        existing.setMail(updated.getMail());
        existing.setCin(updated.getCin());
        existing.setStatue(updated.getStatue()); // (statut)
        existing.setId_etudiant(updated.getId_etudiant());
        existing.setId_filier(updated.getId_filier());

        return enseignantRepository.save(existing);
    }

    // ── Supprimer un enseignant (DELETE) ──────────────────────────────────────
    @Transactional
    public void deleteEnseignant(Long id) {
        if (!enseignantRepository.existsById(id)) {
            throw new RuntimeException("Enseignant introuvable avec l'ID : " + id);
        }
        enseignantRepository.deleteById(id);
    }

}
