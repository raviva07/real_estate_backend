package com.realestate.repository;

import com.realestate.entity.User;
import com.realestate.entity.enums.Role;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // ✅ TEST FIND BY EMAIL (SUCCESS)
    @Test
    void testFindByEmail() {

        // Create user
        User user = new User();
        user.setName("Ravi");
        user.setEmail("ravi@gmail.com");
        user.setPassword("123456");
        user.setRole(Role.ROLE_CUSTOMER);

        userRepository.save(user);

        // Fetch user
        Optional<User> result = userRepository.findByEmail("ravi@gmail.com");

        assertTrue(result.isPresent());
        assertEquals("Ravi", result.get().getName());
    }

    // ✅ TEST FIND BY EMAIL (NOT FOUND)
    @Test
    void testFindByEmailNotFound() {

        Optional<User> result = userRepository.findByEmail("unknown@gmail.com");

        assertFalse(result.isPresent());
    }
}
