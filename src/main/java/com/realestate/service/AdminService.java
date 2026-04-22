package com.realestate.service;

import java.util.List;

import com.realestate.dto.property.PropertyResponse;

public interface AdminService {

    void approveProperty(Long propertyId);
    List<PropertyResponse> getPendingProperties();

    void deleteProperty(Long id);

}

