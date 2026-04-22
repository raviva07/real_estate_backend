package com.realestate.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.realestate.entity.User;
import com.realestate.service.AuthService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = AuthController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class
)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;
    
    @MockBean
    private com.realestate.security.jwt.JwtAuthFilter jwtAuthFilter;

    @MockBean
    private com.realestate.security.jwt.JwtUtil jwtUtil;


    @Test
    void testRegister() throws Exception {

        when(authService.register(any()))
                .thenReturn(new User());

        mockMvc.perform(post("/api/auth/register")
                .contentType("application/json")
                .content("""
                        {
                          "name": "test",
                          "email": "test@gmail.com",
                          "password": "123456",
                          "role": "ROLE_CUSTOMER"
                        }
                        """))
                .andExpect(status().isOk());
    }
}

