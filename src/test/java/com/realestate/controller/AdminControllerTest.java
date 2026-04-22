package com.realestate.controller;

import com.realestate.dto.property.PropertyResponse;
import com.realestate.entity.User;
import com.realestate.repository.UserRepository;
import com.realestate.service.PropertyService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = AdminController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class
)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private com.realestate.security.jwt.JwtAuthFilter jwtAuthFilter;

    @MockBean
    private com.realestate.security.jwt.JwtUtil jwtUtil;

    // ✅ GET USERS
    @Test
    void testGetUsers() throws Exception {

        User user = new User();
        user.setId(1L);

        when(userRepository.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk());
    }

    // ✅ GET PENDING PROPERTIES
    @Test
    void testGetPendingProperties() throws Exception {

        PropertyResponse response = new PropertyResponse();
        response.setId(1L);

        when(propertyService.getPendingProperties())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/admin/properties/pending"))
                .andExpect(status().isOk());
    }

    // ✅ DELETE PROPERTY (ADMIN)
    @Test
    void testDeleteProperty() throws Exception {

        mockMvc.perform(delete("/api/admin/properties/1"))
                .andExpect(status().isOk());
    }

    // ✅ APPROVE PROPERTY
    @Test
    void testApproveProperty() throws Exception {

        PropertyResponse response = new PropertyResponse();
        response.setId(1L);

        when(propertyService.approveProperty(1L)).thenReturn(response);

        mockMvc.perform(put("/api/admin/properties/1/approve"))
                .andExpect(status().isOk());
    }
}
