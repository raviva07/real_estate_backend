package com.realestate.service.impl;

import com.realestate.dto.property.PropertyRequest;
import com.realestate.dto.property.PropertyResponse;
import com.realestate.entity.Property;
import com.realestate.entity.User;
import com.realestate.entity.enums.PropertyStatus;
import com.realestate.entity.enums.PropertyType;
import com.realestate.entity.enums.Role;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.UserRepository;
import com.realestate.service.PropertyService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository,
                               UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    // ✅ ENTITY → DTO
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

    @Override
    public PropertyResponse createProperty(PropertyRequest request, String email) {

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Property p = new Property();
        p.setTitle(request.getTitle());
        p.setDescription(request.getDescription());
        p.setPrice(request.getPrice());
        p.setLocation(request.getLocation());
        p.setType(PropertyType.valueOf(request.getType()));
        p.setStatus(PropertyStatus.PENDING);
        p.setImageUrl(request.getImageUrl()); // ✅ ADD THIS
        p.setOwner(owner);

        return mapToResponse(propertyRepository.save(p));
    }

    @Override
    public PropertyResponse getProperty(Long id) {
        Property p = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        return mapToResponse(p);
    }

    @Override
    public List<PropertyResponse> getAllApprovedProperties() {
        return propertyRepository.findByStatus(PropertyStatus.APPROVED)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyResponse> getMyProperties(String email) {
        return propertyRepository.findByOwnerEmail(email)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyResponse> searchProperties(
            String location,
            PropertyType type,
            Double minPrice,
            Double maxPrice) {

        return propertyRepository.searchProperties(location, type, minPrice, maxPrice)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProperty(Long id, String email) {

        Property p = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (!p.getOwner().getEmail().equals(email) && !isAdmin(email)) {
            throw new RuntimeException("Unauthorized");
        }

        propertyRepository.delete(p);
    }


    @Override
    public PropertyResponse approveProperty(Long id) {

        Property p = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        p.setStatus(PropertyStatus.APPROVED);

        return mapToResponse(propertyRepository.save(p));
    }

    @Override
    public void adminDeleteProperty(Long id) {

        Property p = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        propertyRepository.delete(p);
    }


    private boolean isAdmin(String email) {
        return userRepository.findByEmail(email)
                .map(u -> u.getRole() == Role.ROLE_ADMIN)
                .orElse(false);
    }
    
  
    
    @Override
    public List<PropertyResponse> getPendingProperties() {

        List<Property> properties = propertyRepository.findByStatus(PropertyStatus.PENDING);

        return properties.stream()
                .map(p -> mapToResponse(p))
                .collect(Collectors.toList());
    }
    
    


}
