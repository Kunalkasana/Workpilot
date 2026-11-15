package com.workpilot.ticketservice.repository;

import com.workpilot.ticketservice.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversation, UUID> {

    Optional<Conversation> findByTicketId(UUID ticketId);
}