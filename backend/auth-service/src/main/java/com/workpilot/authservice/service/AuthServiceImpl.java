package com.workpilot.authservice.service;

import com.workpilot.authservice.dto.RegisterRequest;
import com.workpilot.authservice.entity.User;
import com.workpilot.authservice.exception.EmailAlreadyExistsException;
import com.workpilot.authservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}