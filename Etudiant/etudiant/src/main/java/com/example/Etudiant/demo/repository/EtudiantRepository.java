package com.example.Etudiant.demo.repository;

import com.example.Etudiant.demo.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    Optional<Etudiant> findByMatricule(String matricule);
    boolean existsByMatricule(String matricule);
    Optional<Etudiant> findByUserId(Long userId);
}


