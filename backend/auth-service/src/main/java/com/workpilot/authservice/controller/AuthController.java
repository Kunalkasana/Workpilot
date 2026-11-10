package com.workpilot.authservice.controller;

import com.workpilot.authservice.dto.AuthResponse;
import com.workpilot.authservice.dto.RegisterRequest;
import com.workpilot.authservice.entity.User;
import com.workpilot.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {

        User user = authService.register(request);
        AuthResponse response = new AuthResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                null,
                null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}