package com.workpilot.authservice.service;

import com.workpilot.authservice.dto.RegisterRequest;
import com.workpilot.authservice.entity.User;

public interface AuthService {
    User register(RegisterRequest request);
}
