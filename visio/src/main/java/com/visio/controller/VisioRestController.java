package com.visio.controller;

import com.visio.dto.*;
import com.visio.service.ChatVisioService;
import com.visio.service.ParticipantVisioService;
import com.visio.service.SalleVisioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visio")
@RequiredArgsConstructor
public class VisioRestController {

    private final SalleVisioService salleVisioService;
    private final ParticipantVisioService participantVisioService;
    private final ChatVisioService chatVisioService;

    @PostMapping("/salles")
    public ResponseEntity<SalleVisioResponse> creerSalle(
            @Valid @RequestBody SalleVisioRequest request,
            @RequestAttribute("userId") Long userId) {
        return ResponseEntity.ok(
                salleVisioService.creerSalle(request, userId));
    }

    @PostMapping("/salles/{id}/demarrer")
    public ResponseEntity<SalleVisioResponse> demarrer(
            @PathVariable Long id,
            @RequestAttribute("userId") Long userId) {
        return ResponseEntity.ok(
                salleVisioService.demarrerSalle(id, userId));
    }

    @PostMapping("/salles/{id}/terminer")
    public ResponseEntity<SalleVisioResponse> terminer(
            @PathVariable Long id,
            @RequestAttribute("userId") Long userId) {
        return ResponseEntity.ok(
                salleVisioService.terminerSalle(id, userId));
    }

    @GetMapping("/salles")
    public ResponseEntity<List<SalleVisioResponse>> getSalles() {
        return ResponseEntity.ok(salleVisioService.getSalles());
    }

    @GetMapping("/salles/{id}")
    public ResponseEntity<SalleVisioDetailResponse> getDetail(@PathVariable Long id) {
        return ResponseEntity.ok(salleVisioService.getDetail(id));
    }

    @GetMapping("/salles/detail/{id}")
    public ResponseEntity<SalleVisioDetailResponse> getDetailComplet(@PathVariable Long id) {
        return ResponseEntity.ok(salleVisioService.getDetail(id));
    }

    @GetMapping("/salles/code/{code}")
    public ResponseEntity<SalleVisioResponse> getSalleByCode(@PathVariable String code) {
        return ResponseEntity.ok(salleVisioService.getSalleByCode(code));
    }

    @GetMapping("/salles/enseignant/{enseignantId}")
    public ResponseEntity<List<SalleVisioResponse>> getSallesEnseignant(
            @PathVariable Long enseignantId) {
        return ResponseEntity.ok(
                salleVisioService.getSallesParEnseignant(enseignantId));
    }

    @GetMapping("/salles/cours/{coursId}")
    public ResponseEntity<List<SalleVisioResponse>> getSallesCours(
            @PathVariable Long coursId) {
        return ResponseEntity.ok(salleVisioService.getSallesParCours(coursId));
    }

    @GetMapping("/salles/historique")
    public ResponseEntity<List<SalleVisioResponse>> getHistorique() {
        return ResponseEntity.ok(salleVisioService.getHistorique());
    }

    @GetMapping("/salles/en-cours")
    public ResponseEntity<List<SalleVisioResponse>> getSallesEnCours() {
        return ResponseEntity.ok(salleVisioService.getSallesEnCours());
    }

    @PostMapping("/salles/code/{code}/rejoindre")
    public ResponseEntity<JoinSalleResponse> rejoindre(
            @PathVariable String code,
            @RequestBody JoinRequest request,
            @RequestAttribute("userId") Long userId) {
        return ResponseEntity.ok(
                salleVisioService.rejoindreParCode(
                        code, userId, request.getNom(), request.getRole()));
    }

    @PostMapping("/salles/code/{code}/quitter")
    public ResponseEntity<Void> quitter(
            @PathVariable String code,
            @RequestAttribute("userId") Long userId) {
        salleVisioService.quitterParCode(code, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/salles/{id}/participants")
    public ResponseEntity<List<ParticipantVisioResponse>> getParticipants(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                participantVisioService.getParticipants(id));
    }

    @PostMapping("/salles/{id}/participants/deconnexion")
    public ResponseEntity<Void> deconnexionParticipant(
            @PathVariable Long id,
            @RequestAttribute("userId") Long userId) {
        participantVisioService.quitterParUtilisateur(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/salles/{id}/chat")
    public ResponseEntity<List<ChatVisioResponse>> getChat(@PathVariable Long id) {
        return ResponseEntity.ok(chatVisioService.getMessages(id));
    }

    @PostMapping("/salles/{id}/chat")
    public ResponseEntity<ChatVisioResponse> envoyerChat(
            @PathVariable Long id,
            @Valid @RequestBody ChatVisioRequest request) {
        return ResponseEntity.ok(chatVisioService.envoyer(id, request));
    }
}
