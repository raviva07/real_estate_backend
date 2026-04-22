package com.realestate.controller;

import com.realestate.dto.property.PropertyResponse;
import com.realestate.service.ImageService;
import com.realestate.service.PropertyService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = PropertyController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class
)
@AutoConfigureMockMvc(addFilters = false)
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    @MockBean
    private ImageService imageService;

    @MockBean
    private com.realestate.security.jwt.JwtAuthFilter jwtAuthFilter;

    @MockBean
    private com.realestate.security.jwt.JwtUtil jwtUtil;

    @Test
    void testGetAllApprovedProperties() throws Exception {

        PropertyResponse response = new PropertyResponse();
        response.setId(1L);
        response.setTitle("Villa");

        when(propertyService.getAllApprovedProperties())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/properties"))
                .andExpect(status().isOk());
    }
}
