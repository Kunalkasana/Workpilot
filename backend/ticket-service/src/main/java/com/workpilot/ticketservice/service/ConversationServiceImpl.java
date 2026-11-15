package com.workpilot.ticketservice.service;

import com.workpilot.ticketservice.dto.CreateMessageRequest;
import com.workpilot.ticketservice.dto.MessageResponse;
import com.workpilot.ticketservice.entity.Conversation;
import com.workpilot.ticketservice.entity.Message;
import com.workpilot.ticketservice.entity.Ticket;
import com.workpilot.ticketservice.exception.TicketNotFoundException;
import com.workpilot.ticketservice.repository.ConversationRepository;
import com.workpilot.ticketservice.repository.MessageRepository;
import com.workpilot.ticketservice.repository.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final TicketRepository ticketRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    public ConversationServiceImpl(
            TicketRepository ticketRepository,
            ConversationRepository conversationRepository,
            MessageRepository messageRepository,
            ModelMapper modelMapper) {

        this.ticketRepository = ticketRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public MessageResponse addMessage(UUID ticketId, CreateMessageRequest request, UUID senderId) {

        Ticket ticket = findTicket(ticketId);

        // One conversation per ticket: create it on the first message.
        Conversation conversation = conversationRepository.findByTicketId(ticketId)
                .orElseGet(() -> {
                    Conversation created = new Conversation();
                    created.setTicket(ticket);
                    return conversationRepository.save(created);
                });

        Message message = new Message();
        message.setConversation(conversation);
        message.setSenderId(senderId);
        message.setContent(request.getContent());

        Message savedMessage = messageRepository.save(message);

        return modelMapper.map(savedMessage, MessageResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageResponse> getMessages(UUID ticketId) {

        findTicket(ticketId);

        return conversationRepository.findByTicketId(ticketId)
                .map(conversation ->
                        messageRepository
                                .findByConversationIdOrderByCreatedAtAsc(conversation.getId())
                                .stream()
                                .map(message -> modelMapper.map(message, MessageResponse.class))
                                .toList())
                .orElse(List.of());
    }

    @Override
    @Transactional
    public void deleteConversationByTicketId(UUID ticketId) {

        conversationRepository.findByTicketId(ticketId)
                .ifPresent(conversation -> {
                    messageRepository.deleteByConversationId(conversation.getId());
                    conversationRepository.delete(conversation);
                });
    }

    private Ticket findTicket(UUID ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(
                        "Ticket not found with ID: " + ticketId));
    }
}