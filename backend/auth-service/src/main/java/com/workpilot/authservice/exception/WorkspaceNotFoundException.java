package com.workpilot.authservice.exception;

public class WorkspaceNotFoundException extends RuntimeException {

    public WorkspaceNotFoundException(String message) {
        super(message);
    }
}