package com.example.Encadreur.demo.service;

import com.example.Encadreur.demo.entity.Encadreur;
import com.example.Encadreur.demo.repository.EncadreurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EncadreurService {

    private final EncadreurRepository encadreurRepository;

    public EncadreurService(EncadreurRepository encadreurRepository) {
        this.encadreurRepository = encadreurRepository;
    }

    // ── Récupérer tous ──────────────────────────────────────
    public List<Encadreur> getAll() {
        return encadreurRepository.findAll();
    }

    // ── Récupérer par ID ────────────────────────────────────
    public Optional<Encadreur> getById(Long id) {
        return encadreurRepository.findById(id);
    }

    // ── Créer / Modifier ────────────────────────────────────
    public Encadreur save(Encadreur encadreur) {
        return encadreurRepository.save(encadreur);
    }

    // ── Supprimer ───────────────────────────────────────────
    public void delete(Long id) {
        encadreurRepository.deleteById(id);
    }
}