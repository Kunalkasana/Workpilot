package com.workpilot.authservice.exception;

public class InsufficientPermissionException extends RuntimeException {

    public InsufficientPermissionException(String message) {
        super(message);
    }
}