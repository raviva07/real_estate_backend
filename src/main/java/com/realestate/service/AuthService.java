package com.realestate.service;

import com.realestate.dto.auth.LoginRequest;
import com.realestate.dto.auth.RegisterRequest;
import com.realestate.dto.auth.AuthResponse;
import com.realestate.entity.User;

public interface AuthService {

    User register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}


