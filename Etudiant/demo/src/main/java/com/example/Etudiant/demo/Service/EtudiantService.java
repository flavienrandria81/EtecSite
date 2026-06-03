package com.example.Etudiant.demo.Service;

import com.example.Etudiant.demo.Entity.Etudiant;
import com.example.Etudiant.demo.Repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    // ── Récupérer tous les étudiants ──────────────────────────────────────────
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    // ── Récupérer un étudiant par ID ──────────────────────────────────────────
    public Optional<Etudiant> getEtudiantById(Long id) {
        return etudiantRepository.findById(id);
    }

    // ── Récupérer un étudiant par email ───────────────────────────────────────
    public Optional<Etudiant> getEtudiantByEmail(String email) {
        return etudiantRepository.findByEmail(email);
    }

    // ── Récupérer un étudiant par matricule ───────────────────────────────────
    public Optional<Etudiant> getEtudiantByMatricule(String matricule) {
        return etudiantRepository.findByMatricule(matricule);
    }

    // ── Créer un nouvel étudiant ──────────────────────────────────────────────
    public Etudiant createEtudiant(Etudiant etudiant) {
        if (etudiantRepository.existsByEmail(etudiant.getEmail())) {
            throw new RuntimeException("Un étudiant avec cet email existe déjà.");
        }
        if (etudiantRepository.existsByMatricule(etudiant.getMatricule())) {
            throw new RuntimeException("Un étudiant avec ce matricule existe déjà.");
        }
        return etudiantRepository.save(etudiant);
    }

    // ── Mettre à jour un étudiant ─────────────────────────────────────────────
    public Etudiant updateEtudiant(Long id, Etudiant updated) {
        Etudiant existing = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec l'ID : " + id));

        existing.setNom(updated.getNom());
        existing.setPrenom(updated.getPrenom());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setPhoto(updated.getPhoto());
        existing.setGrade(updated.getGrade());
        existing.setMatricule(updated.getMatricule());
        existing.setParcours(updated.getParcours());
        existing.setMentiont(updated.getMentiont());
        existing.setCin(updated.getCin());
        existing.setEncadreur_id(updated.getEncadreur_id());
        existing.setId_emploi_du_temps(updated.getId_emploi_du_temps());
        existing.setCertificat_certifier(updated.getCertificat_certifier());
        existing.setPassword(updated.getPassword());

        return etudiantRepository.save(existing);
    }

    // ── Supprimer un étudiant ─────────────────────────────────────────────────
    public void deleteEtudiant(Long id) {
        if (!etudiantRepository.existsById(id)) {
            throw new RuntimeException("Étudiant introuvable avec l'ID : " + id);
        }
        etudiantRepository.deleteById(id);
    }
}