package com.visio.repository;

import com.visio.entity.ChatVisio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatVisioRepository extends JpaRepository<ChatVisio, Long> {

    List<ChatVisio> findBySalleVisioIdOrderByDateEnvoiAsc(Long salleVisioId);

    List<ChatVisio> findTop100BySalleVisioIdOrderByDateEnvoiAsc(Long salleVisioId);
}
