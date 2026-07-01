package com.example.Encadreur.demo.repository;

import com.example.Encadreur.demo.entity.Encadreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncadreurRepository extends JpaRepository<Encadreur, Long> {

}