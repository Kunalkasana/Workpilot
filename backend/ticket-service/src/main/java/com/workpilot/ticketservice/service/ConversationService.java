package com.workpilot.ticketservice.service;

import com.workpilot.ticketservice.dto.CreateMessageRequest;
import com.workpilot.ticketservice.dto.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface ConversationService {

    MessageResponse addMessage(UUID ticketId, CreateMessageRequest request, UUID senderId);

    List<MessageResponse> getMessages(UUID ticketId);

    void deleteConversationByTicketId(UUID ticketId);
}