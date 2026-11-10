package com.workpilot.authservice.controller;

import com.workpilot.authservice.dto.AuthResponse;
import com.workpilot.authservice.dto.RegisterRequest;
import com.workpilot.authservice.entity.User;
import com.workpilot.authservice.service.AuthService;
import com.workpilot.authservice.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {

        User user = authService.register(request);
        String token = jwtService.generateToken(user);
        AuthResponse response = new AuthResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                token,
                "Bearer"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}