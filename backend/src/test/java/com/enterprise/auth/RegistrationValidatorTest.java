package com.enterprise.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationValidatorTest {

    @Test
    void validRegistrationPassesValidation() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("jane.doe");
        request.setEmail("jane.doe@example.com");
        request.setPassword("SecurePass!123");
        request.setConfirmPassword("SecurePass!123");

        ValidationResult result = RegistrationValidator.validate(request);

        assertTrue(result.isValid());
        assertNull(result.getErrorMessage());
    }

    @Test
    void mismatchedPasswordsFailValidation() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("jane.doe");
        request.setEmail("jane.doe@example.com");
        request.setPassword("SecurePass!123");
        request.setConfirmPassword("Different!123");

        ValidationResult result = RegistrationValidator.validate(request);

        assertFalse(result.isValid());
        assertEquals("Password and confirm password must match exactly.", result.getErrorMessage());
    }

    @Test
    void duplicateEmailIsRejected() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("john.doe");
        request.setEmail("john@example.com");
        request.setPassword("SecurePass!123");
        request.setConfirmPassword("SecurePass!123");

        UserRepository repository = new InMemoryUserRepository();
        repository.save(new User(1L, "existing.user", "john@example.com", "hash"));

        ValidationResult result = RegistrationValidator.validate(request, repository);

        assertFalse(result.isValid());
        assertEquals("An account with this email already exists.", result.getErrorMessage());
    }
}
