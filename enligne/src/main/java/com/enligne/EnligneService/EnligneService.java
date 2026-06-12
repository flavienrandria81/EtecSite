package com.enligne.EnligneService;

import com.enligne.Entity.Enligne;
import com.enligne.Repository.EnligneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnligneService {


    private final EnligneRepository enligneRepository;

    public EnligneService(EnligneRepository enligneRepository) {
        this.enligneRepository = enligneRepository;
    }

    public List<Enligne> getAll() {
        return enligneRepository.findAll();
    }

    public Optional<Enligne> getById(Long id_video) {
        return enligneRepository.findById(id_video);
    }

    public Enligne save(Enligne encadreur) {
        return enligneRepository.save(encadreur);
    }

    public void delete(Long id_video) {
        enligneRepository.deleteById(id_video);
    }
}
