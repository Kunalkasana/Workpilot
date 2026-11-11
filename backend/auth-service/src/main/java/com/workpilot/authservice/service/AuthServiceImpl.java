package com.workpilot.authservice.service;

import com.workpilot.authservice.dto.RegisterRequest;
import com.workpilot.authservice.entity.User;
import com.workpilot.authservice.exception.EmailAlreadyExistsException;
import com.workpilot.authservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.workpilot.authservice.dto.LoginRequest;
import com.workpilot.authservice.exception.InvalidCredentialsException;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    @Transactional
    public User register(RegisterRequest request) {
        // Check whether email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }
        // Hash the password
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        // Create User entity
        User user = new User(
                request.getFullName(),
                request.getEmail(),
                hashedPassword,
                true
        );
        // Save user in database
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User login(LoginRequest request) {

        // 1. Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password")
                );

        // 2. Check whether account is enabled
        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // 3. Verify raw password against stored BCrypt hash
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash()
        )) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // 4. Return authenticated user
        return user;
    }

}