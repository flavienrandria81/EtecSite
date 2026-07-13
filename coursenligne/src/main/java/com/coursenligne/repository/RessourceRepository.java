package com.coursenligne.repository;


import com.coursenligne.entity.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RessourceRepository
        extends JpaRepository<Ressource, Long> {


    // Trouver les ressources d'une leçon
    List<Ressource> findByLeconId(Long leconId);



    // Trouver les ressources par type
    List<Ressource> findByType(String type);

}