package com.enligne.Repository;

import com.enligne.Entity.Enligne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnligneRepository extends JpaRepository<Enligne, Long> {

}
