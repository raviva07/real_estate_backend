package com.realestate.service;

import com.realestate.dto.property.PropertyResponse;
import com.realestate.entity.Property;
import com.realestate.entity.User;
import com.realestate.entity.enums.PropertyStatus;
import com.realestate.entity.enums.PropertyType;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.UserRepository;
import com.realestate.service.impl.PropertyServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    // ✅ TEST APPROVED PROPERTIES
    @Test
    void testGetAllApprovedProperties() {

        Property property = new Property();
        property.setStatus(PropertyStatus.APPROVED);

        User user = new User();
        user.setName("Admin");
        property.setOwner(user);

        when(propertyRepository.findByStatus(PropertyStatus.APPROVED))
                .thenReturn(List.of(property));

        List<PropertyResponse> list = propertyService.getAllApprovedProperties();

        assertEquals(1, list.size());
        verify(propertyRepository).findByStatus(PropertyStatus.APPROVED);
    }

    // ✅ TEST SEARCH
    @Test
    void testSearchProperties() {

        Property property = new Property();
        User user = new User();
        user.setName("User");
        property.setOwner(user);

        when(propertyRepository.searchProperties(any(), any(), any(), any()))
                .thenReturn(List.of(property));

        List<PropertyResponse> result = propertyService.searchProperties(
                "Hyd", PropertyType.SALE, null, null
        );

        assertFalse(result.isEmpty());
    }

    // ✅ TEST GET MY PROPERTIES
    @Test
    void testGetMyProperties() {

        Property property = new Property();
        User user = new User();
        user.setName("User");
        property.setOwner(user);

        when(propertyRepository.findByOwnerEmail("test@gmail.com"))
                .thenReturn(List.of(property));

        List<PropertyResponse> result = propertyService.getMyProperties("test@gmail.com");

        assertEquals(1, result.size());
    }

    // ✅ TEST APPROVE PROPERTY
    @Test
    void testApproveProperty() {

        Property property = new Property();
        property.setStatus(PropertyStatus.PENDING);

        User user = new User();
        user.setName("Admin");
        property.setOwner(user);

        when(propertyRepository.findById(1L))
                .thenReturn(Optional.of(property));

        when(propertyRepository.save(property)).thenReturn(property);

        PropertyResponse result = propertyService.approveProperty(1L);

        assertEquals(PropertyStatus.APPROVED, result.getStatus());
    }
}
