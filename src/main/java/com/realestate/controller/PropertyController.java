package com.realestate.controller;

import com.realestate.dto.ImageUrlRequest;
import com.realestate.dto.property.PropertyRequest;
import com.realestate.dto.property.PropertyResponse;
import com.realestate.entity.enums.PropertyType;
import com.realestate.service.ImageService;
import com.realestate.service.PropertyService;

import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;
    private final ImageService imageService;

    public PropertyController(PropertyService propertyService,
                              ImageService imageService) {
        this.propertyService = propertyService;
        this.imageService = imageService;
    }

    // ✅ IMAGE
    @PostMapping(value = "/upload-image", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        return imageService.uploadImage(file);
    }
    @PostMapping("/upload-image-url")
    public String uploadImageUrl(@RequestBody ImageUrlRequest request) {
        return imageService.uploadImageFromUrl(request.getUrl());
    }



    // ✅ CREATE
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public PropertyResponse createProperty(@RequestBody PropertyRequest request,
                                           Authentication auth) {
        return propertyService.createProperty(request, auth.getName());
    }

    // PUBLIC
    @GetMapping
    public List<PropertyResponse> getAll() {
        return propertyService.getAllApprovedProperties();
    }

    @GetMapping("/{id}")
    public PropertyResponse getOne(@PathVariable Long id) {
        return propertyService.getProperty(id);
    }

    // USER
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public List<PropertyResponse> my(Authentication auth) {
        return propertyService.getMyProperties(auth.getName());
    }

    @GetMapping("/search")
    public List<PropertyResponse> search(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) PropertyType type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return propertyService.searchProperties(location, type, minPrice, maxPrice);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public void delete(@PathVariable Long id, Authentication auth) {
        propertyService.deleteProperty(id, auth.getName());
    }
}
