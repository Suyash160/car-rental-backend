package com.suyash.carrental.service;

import com.suyash.carrental.dto.AuthResponse;
import com.suyash.carrental.dto.LoginRequest;
import com.suyash.carrental.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
