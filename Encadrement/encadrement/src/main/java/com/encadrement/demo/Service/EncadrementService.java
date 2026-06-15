package com.encadrement.demo.Service;

import com.encadrement.demo.Entity.Encadrement;
import com.encadrement.demo.Repository.EncadrementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EncadrementService {

    @Autowired
    private EncadrementRepository encadrementRepository;

    // Récupérer tous les encadrements
    public List<Encadrement> getAllEncadrements() {
        return encadrementRepository.findAll();
    }

    // Récupérer un encadrement par son ID
    public Optional<Encadrement> getEncadrementById(Long id) {
        return encadrementRepository.findById(id);
    }

    // Créer un nouvel encadrement
    public Encadrement createEncadrement(Encadrement encadrement) {
        return encadrementRepository.save(encadrement);
    }

    // Mettre à jour un encadrement
    public Encadrement updateEncadrement(Long id, Encadrement encadrementDetails) {
        Encadrement encadrement = encadrementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Encadrement non trouvé avec l'id: " + id));

        encadrement.setDescription(encadrementDetails.getDescription());
        encadrement.setTheme(encadrementDetails.getTheme());

        return encadrementRepository.save(encadrement);
    }

    // Supprimer un encadrement
    public void deleteEncadrement(Long id) {
        Encadrement encadrement = encadrementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Encadrement non trouvé avec l'id: " + id));
        encadrementRepository.delete(encadrement);
    }

}