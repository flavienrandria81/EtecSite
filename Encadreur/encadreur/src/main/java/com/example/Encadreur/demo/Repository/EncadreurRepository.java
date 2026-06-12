package com.example.Encadreur.demo.Repository;

import com.example.Encadreur.demo.Entity.Encadreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncadreurRepository extends JpaRepository<Encadreur, Long> {

}