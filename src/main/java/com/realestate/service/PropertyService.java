package com.realestate.service;

import com.realestate.dto.property.PropertyRequest;
import com.realestate.dto.property.PropertyResponse;
import com.realestate.entity.enums.PropertyType;

import java.util.List;

public interface PropertyService {

    PropertyResponse createProperty(PropertyRequest request, String email);

    PropertyResponse getProperty(Long id);

    List<PropertyResponse> getAllApprovedProperties();

    List<PropertyResponse> getMyProperties(String email);
    
    List<PropertyResponse> getPendingProperties();
    


    List<PropertyResponse> searchProperties(
            String location,
            PropertyType type,
            Double minPrice,
            Double maxPrice
    );

    void deleteProperty(Long id, String email);

    PropertyResponse approveProperty(Long id);

    void adminDeleteProperty(Long id);
}


