package com.encadrement.demo.Repository;

import com.encadrement.demo.Entity.Encadrement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncadrementRepository extends JpaRepository<Encadrement, Long> {

}
