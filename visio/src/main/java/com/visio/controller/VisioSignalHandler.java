package com.visio.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visio.dto.ChatSocketMessage;
import com.visio.dto.ChatVisioRequest;
import com.visio.dto.ChatVisioResponse;
import com.visio.dto.ParticipantVisioRequest;
import com.visio.dto.ParticipantVisioResponse;
import com.visio.dto.SignalMessage;
import com.visio.entity.RoleParticipant;
import com.visio.entity.SalleVisio;
import com.visio.repository.SalleVisioRepository;
import com.visio.service.ChatVisioService;
import com.visio.service.ParticipantVisioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class VisioSignalHandler {

    private static final String ATTR_SESSION = "visioSessionId";
    private static final String ATTR_SALLE = "visioSalleId";
    private static final String ATTR_CODE = "visioCode";
    private static final String ATTR_USER = "visioUserId";

    private final SimpMessagingTemplate messagingTemplate;
    private final ParticipantVisioService participantVisioService;
    private final ChatVisioService chatVisioService;
    private final SalleVisioRepository salleVisioRepository;
    private final ObjectMapper objectMapper;

    @MessageMapping("/reunion/{code}/join")
    public void join(@DestinationVariable String code,
                     SignalMessage message,
                     SimpMessageHeaderAccessor headerAccessor) {

        SalleVisio salle = trouverSalle(code);

        String serverSessionId = UUID.randomUUID().toString();
        String clientId = message.getFrom();

        Map<String, Object> infos = lirePayload(message.getPayload());

        Long userId = infos.get("userId") == null
                ? null : Long.valueOf(infos.get("userId").toString());
        String nom = infos.get("nom") == null
                ? "Participant" : infos.get("nom").toString();
        String role = infos.get("role") == null
                ? "ETUDIANT" : infos.get("role").toString();

        RoleParticipant roleParticipant =
                "ENSEIGNANT".equalsIgnoreCase(role)
                        ? RoleParticipant.ENSEIGNANT
                        : RoleParticipant.ETUDIANT;

        ParticipantVisioResponse participant =
                participantVisioService.rejoindre(
                        salle.getId(),
                        ParticipantVisioRequest.builder()
                                .utilisateurId(userId == null ? 0L : userId)
                                .nom(nom)
                                .role(roleParticipant)
                                .build(),
                        serverSessionId);

        headerAccessor.getSessionAttributes().put(ATTR_SESSION, serverSessionId);
        headerAccessor.getSessionAttributes().put(ATTR_SALLE, salle.getId());
        headerAccessor.getSessionAttributes().put(ATTR_CODE, code);
        headerAccessor.getSessionAttributes().put(ATTR_USER, userId == null ? 0L : userId);

        Map<String, Object> welcome = new HashMap<>();
        welcome.put("type", "welcome");
        welcome.put("to", clientId);
        welcome.put("sessionId", serverSessionId);
        welcome.put("salleId", salle.getId());

        messagingTemplate.convertAndSend(
                "/topic/reunion/" + code + "/welcome", welcome);

        Map<String, Object> presence = new HashMap<>();
        presence.put("type", "peer-joined");
        presence.put("payload", construirePresence(participant));

        messagingTemplate.convertAndSend(
                "/topic/reunion/" + code + "/presence", presence);
    }

    @MessageMapping("/reunion/{code}/signal")
    public void signal(@DestinationVariable String code, SignalMessage message) {
        messagingTemplate.convertAndSend(
                "/topic/reunion/" + code + "/signal", message);
    }

    @MessageMapping("/reunion/{code}/raisehand")
    public void raiseHand(@DestinationVariable String code, SignalMessage message) {
        messagingTemplate.convertAndSend(
                "/topic/reunion/" + code + "/presence", message);
    }

    @MessageMapping("/reunion/{code}/chat")
    public void chat(@DestinationVariable String code,
                     ChatSocketMessage message,
                     SimpMessageHeaderAccessor headerAccessor) {

        SalleVisio salle = trouverSalle(code);

        Map<String, Object> attrs = headerAccessor.getSessionAttributes();
        Long userId = attrs == null || attrs.get(ATTR_USER) == null
                ? 0L : Long.valueOf(attrs.get(ATTR_USER).toString());

        ChatVisioResponse saved = chatVisioService.envoyer(
                salle.getId(),
                ChatVisioRequest.builder()
                        .utilisateurId(userId)
                        .nom(message.getNom())
                        .message(message.getMessage())
                        .build());

        Map<String, Object> broadcast = new HashMap<>();
        broadcast.put("from", message.getFrom());
        broadcast.put("nom", saved.getNom());
        broadcast.put("message", saved.getMessage());
        broadcast.put("dateEnvoi", saved.getDateEnvoi() != null
                ? saved.getDateEnvoi().toString()
                : LocalDateTime.now().toString());

        messagingTemplate.convertAndSend(
                "/topic/reunion/" + code + "/chat", broadcast);
    }

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {

        StompHeaderAccessor headers =
                StompHeaderAccessor.wrap(event.getMessage());

        Map<String, Object> attrs = headers.getSessionAttributes();
        if (attrs == null) return;

        String sessionId = (String) attrs.get(ATTR_SESSION);
        String code = (String) attrs.get(ATTR_CODE);
        Long salleId = attrs.get(ATTR_SALLE) == null
                ? null : Long.valueOf(attrs.get(ATTR_SALLE).toString());

        if (sessionId == null || salleId == null) return;

        try {
            participantVisioService.quitter(salleId, sessionId);
        } catch (Exception e) {
            log.debug("Participant déjà déconnecté : {}", e.getMessage());
        }

        if (code != null) {
            Map<String, Object> presence = new HashMap<>();
            presence.put("type", "peer-left");
            presence.put("payload", Map.of("sessionId", sessionId));

            messagingTemplate.convertAndSend(
                    "/topic/reunion/" + code + "/presence", presence);
        }
    }

    private SalleVisio trouverSalle(String code) {
        return salleVisioRepository
                .findByCodeAcces(code.toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Salle introuvable avec le code " + code));
    }

    private Map<String, Object> lirePayload(String payload) {
        Map<String, Object> result = new HashMap<>();
        if (payload == null || payload.isBlank()) return result;
        try {
            JsonNode node = objectMapper.readTree(payload);
            if (node.has("userId") && !node.get("userId").isNull()) {
                result.put("userId", node.get("userId").asLong());
            }
            if (node.has("nom")) result.put("nom", node.get("nom").asText());
            if (node.has("role")) result.put("role", node.get("role").asText());
        } catch (Exception e) {
            log.warn("Payload de jointure invalide : {}", e.getMessage());
        }
        return result;
    }

    private Map<String, Object> construirePresence(ParticipantVisioResponse p) {
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", p.getSessionId());
        map.put("nom", p.getNom());
        map.put("role", p.getRole() != null ? p.getRole().name() : "ETUDIANT");
        return map;
    }
}
