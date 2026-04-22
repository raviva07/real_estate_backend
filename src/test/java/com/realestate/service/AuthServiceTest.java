package com.realestate.service;

import com.realestate.dto.auth.AuthResponse;
import com.realestate.dto.auth.LoginRequest;
import com.realestate.dto.auth.RegisterRequest;
import com.realestate.entity.User;
import com.realestate.entity.enums.Role;
import com.realestate.repository.UserRepository;
import com.realestate.security.jwt.JwtUtil;
import com.realestate.service.impl.AuthServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    // ✅ REGISTER TEST
    @Test
    void testRegisterUser() {

        RegisterRequest req = new RegisterRequest();
        req.setName("Test");
        req.setEmail("test@gmail.com");
        req.setPassword("123");
        req.setRole(Role.ROLE_CUSTOMER);

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("123"))
                .thenReturn("encoded");

        User saved = new User();
        saved.setEmail("test@gmail.com");

        when(userRepository.save(any(User.class))).thenReturn(saved);

        User result = authService.register(req);

        assertEquals("test@gmail.com", result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    // ✅ LOGIN TEST
    @Test
    void testLogin() {

        LoginRequest req = new LoginRequest();
        req.setEmail("test@gmail.com");
        req.setPassword("123");

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("encoded");
        user.setRole(Role.ROLE_CUSTOMER);

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("123", "encoded"))
                .thenReturn(true);

        when(jwtUtil.generateToken("test@gmail.com", "ROLE_CUSTOMER"))
                .thenReturn("token123");

        AuthResponse response = authService.login(req);

        assertEquals("token123", response.getToken());
    }
}
