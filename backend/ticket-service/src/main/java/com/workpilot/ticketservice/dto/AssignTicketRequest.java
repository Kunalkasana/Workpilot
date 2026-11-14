package com.workpilot.ticketservice.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class AssignTicketRequest {

    @NotNull(message = "Assigned user ID is required")
    private UUID assignedTo;

    public UUID getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UUID assignedTo) {
        this.assignedTo = assignedTo;
    }
}