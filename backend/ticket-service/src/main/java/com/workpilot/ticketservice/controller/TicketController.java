package com.workpilot.ticketservice.controller;

import com.workpilot.ticketservice.service.TicketService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.workpilot.ticketservice.dto.CreateTicketRequest;
import com.workpilot.ticketservice.dto.TicketResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.UUID;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.workpilot.ticketservice.dto.UpdateTicketRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.workpilot.ticketservice.dto.AssignTicketRequest;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(
            @Valid @RequestBody CreateTicketRequest request) {
        // Temporary test UUID.
        // Later, this will come from the authenticated user.
        UUID createdBy = UUID.fromString(
                "550e8400-e29b-41d4-a716-446655440000"
        );

        TicketResponse response =
                ticketService.createTicket(request, createdBy);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets() {

        List<TicketResponse> tickets = ticketService.getAllTickets();

        return ResponseEntity.ok(tickets);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(
            @PathVariable UUID id) {

        TicketResponse response = ticketService.getTicketById(id);

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> updateTicket(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTicketRequest request) {

        TicketResponse response = ticketService.updateTicket(id, request);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {

        ticketService.deleteTicket(id);

        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}/assign")
    public ResponseEntity<TicketResponse> assignTicket(
            @PathVariable UUID id,
            @Valid @RequestBody AssignTicketRequest request) {

        TicketResponse response =
                ticketService.assignTicket(id, request);

        return ResponseEntity.ok(response);
    }
}
