package com.visio.service;

import com.visio.dto.*;

import java.util.List;

public interface SalleVisioService {

    SalleVisioResponse creerSalle(SalleVisioRequest request, Long userId);

    SalleVisioResponse demarrerSalle(Long salleId, Long userId);

    SalleVisioResponse terminerSalle(Long salleId, Long userId);

    JoinSalleResponse rejoindreParCode(String codeAcces, Long userId, String nom, String role);

    void quitterParCode(String codeAcces, Long userId);

    SalleVisioResponse getSalle(Long id);

    SalleVisioDetailResponse getDetail(Long id);

    SalleVisioResponse getSalleByCode(String codeAcces);

    List<SalleVisioResponse> getSalles();

    List<SalleVisioResponse> getSallesParEnseignant(Long enseignantId);

    List<SalleVisioResponse> getSallesParCours(Long coursId);

    List<SalleVisioResponse> getHistorique();

    List<SalleVisioResponse> getSallesEnCours();
}
