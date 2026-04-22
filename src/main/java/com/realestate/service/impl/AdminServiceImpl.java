package com.realestate.service.impl;

import com.realestate.dto.property.PropertyResponse;
import com.realestate.entity.Property;
import com.realestate.entity.enums.PropertyStatus;
import com.realestate.repository.PropertyRepository;
import com.realestate.service.AdminService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final PropertyRepository propertyRepository;

    public AdminServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public void approveProperty(Long propertyId) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        property.setStatus(PropertyStatus.APPROVED);


        propertyRepository.save(property);
    }
    

    @Override
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
    
    @Override
    public List<PropertyResponse> getPendingProperties() {

        return propertyRepository.findByStatus(PropertyStatus.PENDING)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PropertyResponse mapToResponse(Property p) {
        PropertyResponse r = new PropertyResponse();
        r.setId(p.getId());
        r.setTitle(p.getTitle());
        r.setDescription(p.getDescription());
        r.setPrice(p.getPrice());
        r.setType(p.getType());
        r.setLocation(p.getLocation());
        r.setImageUrl(p.getImageUrl());
        r.setStatus(p.getStatus());
        r.setOwnerName(p.getOwner().getName());
        return r;
    }   
    }


