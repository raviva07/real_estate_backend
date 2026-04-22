package com.realestate.repository;

import com.realestate.entity.Property;
import com.realestate.entity.User;
import com.realestate.entity.enums.PropertyStatus;
import com.realestate.entity.enums.PropertyType;
import com.realestate.entity.enums.Role;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PropertyRepositoryTest {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository; // ✅ ADD THIS
    
    

    @Test
    void testSearchProperties() {

        // ✅ Create user
        User user = new User();
        user.setName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("1234567");
        user.setRole(Role.ROLE_CUSTOMER);

        user = userRepository.save(user);

        // ✅ Create property
        Property p = new Property();
        p.setTitle("Villa");
        p.setLocation("Hyderabad");
        p.setPrice(500000.0);
        p.setType(PropertyType.SALE);
        p.setStatus(PropertyStatus.APPROVED);
        p.setOwner(user); // 🔥 IMPORTANT

        propertyRepository.save(p);

        // ✅ Test search
        List<Property> result = propertyRepository.searchProperties(
                "Hyd", PropertyType.SALE, 100000.0, 600000.0
        );

        assertFalse(result.isEmpty());
    }
}
