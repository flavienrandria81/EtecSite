package com.visio.service;

import com.visio.dto.ChatVisioRequest;
import com.visio.dto.ChatVisioResponse;

import java.util.List;

public interface ChatVisioService {

    ChatVisioResponse envoyer(Long salleVisioId, ChatVisioRequest request);

    List<ChatVisioResponse> getMessages(Long salleVisioId);
}
