package com.visio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visio.config.VisioProperties;
import com.visio.dto.SalleVisioResponse;
import com.visio.service.SalleVisioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class VisioWebController {

    private final SalleVisioService salleVisioService;
    private final VisioProperties visioProperties;
    private final ObjectMapper objectMapper;

    @GetMapping("/visio")
    public String index(Model model) {
        model.addAttribute("salles", salleVisioService.getSalles());
        model.addAttribute("historique", salleVisioService.getHistorique());
        return "visio";
    }

    @GetMapping("/visio/room/{code}")
    public String room(@PathVariable String code, Model model) {
        SalleVisioResponse salle = salleVisioService.getSalleByCode(code);
        model.addAttribute("salle", salle);
        model.addAttribute("roomCode", salle.getCodeAcces());
        model.addAttribute("turnConfig", buildTurnConfigJson());
        return "visioRoom";
    }

    private String buildTurnConfigJson() {
        try {
            Map<String, Object> config = new HashMap<>();
            List<Map<String, String>> iceServers = new ArrayList<>();

            Map<String, String> stun = new HashMap<>();
            stun.put("urls", "stun:stun.l.google.com:19302");
            iceServers.add(stun);

            if (visioProperties.getTurnUrl() != null
                    && !visioProperties.getTurnUrl().isBlank()) {
                Map<String, String> turn = new HashMap<>();
                turn.put("urls", visioProperties.getTurnUrl());
                turn.put("username", visioProperties.getTurnUsername());
                turn.put("credential", visioProperties.getTurnPassword());
                iceServers.add(turn);
            }

            config.put("iceServers", iceServers);
            return objectMapper.writeValueAsString(config);
        } catch (Exception e) {
            return "{\"iceServers\":[]}";
        }
    }
}
