package com.realestate.controller;

import com.realestate.entity.User;
import com.realestate.dto.property.PropertyResponse;
import com.realestate.service.PropertyService;
import com.realestate.repository.UserRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final PropertyService propertyService;

    public AdminController(UserRepository userRepository,
                           PropertyService propertyService) {
        this.userRepository = userRepository;
        this.propertyService = propertyService;
    }

    // ================= USERS =================

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id,
                           @RequestBody User updatedUser) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(updatedUser.getName());
        user.setRole(updatedUser.getRole());

        return userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    // ================= PROPERTIES =================

    @PutMapping("/properties/{id}/approve")
    public PropertyResponse approve(@PathVariable Long id) {
        return propertyService.approveProperty(id);
    }

    // ✅ ADMIN DELETE ANY PROPERTY (ONLY ONE METHOD)
    @DeleteMapping("/properties/{id}")
    public void deleteProperty(@PathVariable Long id) {
        propertyService.adminDeleteProperty(id);
    }

    // ✅ GET PENDING
    @GetMapping("/properties/pending")
    public List<PropertyResponse> getPendingProperties() {
        return propertyService.getPendingProperties();
    }
}
