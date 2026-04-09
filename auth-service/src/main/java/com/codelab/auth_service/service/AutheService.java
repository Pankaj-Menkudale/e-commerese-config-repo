package com.codelab.auth_service.service;

import com.codelab.auth_service.dto.AuthResponse;
import com.codelab.auth_service.dto.LoginRequest;
import com.codelab.auth_service.dto.RegisterRequest;

public interface AutheService {

    public String register(RegisterRequest request);

    public AuthResponse login(LoginRequest request);


    }
