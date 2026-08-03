package com.visio.service;

import com.visio.dto.ChatVisioRequest;
import com.visio.dto.ChatVisioResponse;
import com.visio.entity.ChatVisio;
import com.visio.repository.ChatVisioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatVisioServiceImpl implements ChatVisioService {

    private final ChatVisioRepository repository;

    @Override
    public ChatVisioResponse envoyer(Long salleVisioId, ChatVisioRequest request) {

        ChatVisio chat = ChatVisio.builder()
                .salleVisioId(salleVisioId)
                .utilisateurId(request.getUtilisateurId())
                .nom(request.getNom())
                .message(request.getMessage())
                .dateEnvoi(LocalDateTime.now())
                .build();

        return toResponse(repository.save(chat));
    }

    @Override
    public List<ChatVisioResponse> getMessages(Long salleVisioId) {
        return repository
                .findTop100BySalleVisioIdOrderByDateEnvoiAsc(salleVisioId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ChatVisioResponse toResponse(ChatVisio c) {
        return ChatVisioResponse.builder()
                .id(c.getId())
                .salleVisioId(c.getSalleVisioId())
                .utilisateurId(c.getUtilisateurId())
                .nom(c.getNom())
                .message(c.getMessage())
                .dateEnvoi(c.getDateEnvoi())
                .build();
    }
}
