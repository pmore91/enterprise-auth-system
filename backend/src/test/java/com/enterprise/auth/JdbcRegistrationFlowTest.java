package com.enterprise.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JdbcRegistrationFlowTest {
    @Test
    void registerUserViaJdbcRepository() {
        JdbcUserRepository repository = new JdbcUserRepository();
        RegistrationService service = new RegistrationService(repository);

        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("alice");
        request.setEmail("alice@example.com");
        request.setPassword("StrongPass123!");
        request.setConfirmPassword("StrongPass123!");

        ApiResponse response = service.register(request);

        assertNotNull(response);
        assertEquals("success", response.getStatus());
        assertEquals("User registered successfully", response.getMessage());
    }
}
