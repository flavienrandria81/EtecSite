package com.example.Enseignant.demo.Repository;

import com.example.Enseignant.demo.Entity.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {

    Optional<Enseignant> findByMail(String mail);

    boolean existsByMail(String mail);

}
