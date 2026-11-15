package com.workpilot.ticketservice.controller;

import com.workpilot.ticketservice.dto.CreateMessageRequest;
import com.workpilot.ticketservice.dto.MessageResponse;
import com.workpilot.ticketservice.service.ConversationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets/{ticketId}/messages")
public class MessageController {

    private final ConversationService conversationService;

    public MessageController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> addMessage(
            @PathVariable UUID ticketId,
            @Valid @RequestBody CreateMessageRequest request) {

        // Temporary test UUID.
        // Later, this will come from the authenticated user.
        UUID senderId = UUID.fromString(
                "550e8400-e29b-41d4-a716-446655440000"
        );

        MessageResponse response =
                conversationService.addMessage(ticketId, request, senderId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MessageResponse>> getMessages(
            @PathVariable UUID ticketId) {

        return ResponseEntity.ok(conversationService.getMessages(ticketId));
    }
}