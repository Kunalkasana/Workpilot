package com.workpilot.ticketservice.service;

import com.workpilot.ticketservice.dto.CreateTicketRequest;
import com.workpilot.ticketservice.dto.TicketResponse;
import com.workpilot.ticketservice.dto.UpdateTicketRequest;
import com.workpilot.ticketservice.repository.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.workpilot.ticketservice.exception.TicketNotFoundException;
import com.workpilot.ticketservice.entity.Ticket;
import java.util.UUID;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;

    public TicketServiceImpl(
            TicketRepository ticketRepository,
            ModelMapper modelMapper) {

        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TicketResponse createTicket(CreateTicketRequest request, UUID createdBy) {

        Ticket ticket = modelMapper.map(request, Ticket.class);

        ticket.setCreatedBy(createdBy);

        Ticket savedTicket = ticketRepository.save(ticket);

        return modelMapper.map(savedTicket, TicketResponse.class);
    }

    @Override
    public TicketResponse getTicketById(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));
        return modelMapper.map(ticket, TicketResponse.class);
    }

    @Override
    public List<TicketResponse> getAllTickets() {

        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketResponse.class))
                .toList();
    }

    @Override
    public TicketResponse updateTicket(UUID ticketId, UpdateTicketRequest request) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));

        modelMapper.map(request, ticket);

        Ticket updatedTicket = ticketRepository.save(ticket);

        return modelMapper.map(updatedTicket, TicketResponse.class);
    }

    @Override
    public void deleteTicket(UUID ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));

        ticketRepository.delete(ticket);
    }
}