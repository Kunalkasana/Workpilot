package com.workpilot.ticketservice.service;

import com.workpilot.ticketservice.dto.CreateTicketRequest;
import com.workpilot.ticketservice.dto.UpdateTicketRequest;
import com.workpilot.ticketservice.dto.TicketResponse;

import java.util.List;
import java.util.UUID;

public interface TicketService {

    TicketResponse createTicket(CreateTicketRequest request, UUID createdBy);

    TicketResponse getTicketById(UUID ticketId);

    List<TicketResponse> getAllTickets();

    TicketResponse updateTicket(UUID ticketId, UpdateTicketRequest request);

    void deleteTicket(UUID ticketId);
}
