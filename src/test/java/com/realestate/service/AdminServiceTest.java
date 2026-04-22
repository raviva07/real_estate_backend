package com.realestate.service;

import com.realestate.dto.property.PropertyResponse;
import com.realestate.entity.Property;
import com.realestate.entity.User;
import com.realestate.entity.enums.PropertyStatus;
import com.realestate.repository.PropertyRepository;
import com.realestate.service.impl.AdminServiceImpl;

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
class AdminServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    // ✅ TEST APPROVE PROPERTY
    @Test
    void testApproveProperty() {

        Property property = new Property();
        property.setId(1L);
        property.setStatus(PropertyStatus.PENDING);

        when(propertyRepository.findById(1L))
                .thenReturn(Optional.of(property));

        adminService.approveProperty(1L);

        assertEquals(PropertyStatus.APPROVED, property.getStatus());
        verify(propertyRepository).save(property);
    }

    // ✅ TEST GET PENDING PROPERTIES
    @Test
    void testGetPendingProperties() {

        Property property = new Property();
        property.setStatus(PropertyStatus.PENDING);

        User user = new User();
        user.setName("Test User");
        property.setOwner(user);

        when(propertyRepository.findByStatus(PropertyStatus.PENDING))
                .thenReturn(List.of(property));

        List<PropertyResponse> result = adminService.getPendingProperties();

        assertEquals(1, result.size());
        assertEquals("Test User", result.get(0).getOwnerName());

        verify(propertyRepository).findByStatus(PropertyStatus.PENDING);
    }

    // ✅ TEST DELETE PROPERTY
    @Test
    void testDeleteProperty() {

        adminService.deleteProperty(1L);

        verify(propertyRepository).deleteById(1L);
    }
}
