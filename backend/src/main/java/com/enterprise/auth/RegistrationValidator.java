package com.enterprise.auth;

import java.util.Objects;

public class RegistrationValidator {

    public static ValidationResult validate(RegistrationRequest request) {
        return validate(request, new InMemoryUserRepository());
    }

    public static ValidationResult validate(RegistrationRequest request, UserRepository repository) {
        if (request == null) {
            return ValidationResult.failure("Registration request is required.");
        }

        if (isBlank(request.getUsername())) {
            return ValidationResult.failure("Username is required.");
        }

        if (isBlank(request.getEmail()) || !request.getEmail().contains("@")) {
            return ValidationResult.failure("A valid email is required.");
        }

        if (isBlank(request.getPassword())) {
            return ValidationResult.failure("Password is required.");
        }

        if (isBlank(request.getConfirmPassword())) {
            return ValidationResult.failure("Password confirmation is required.");
        }

        if (!Objects.equals(request.getPassword(), request.getConfirmPassword())) {
            return ValidationResult.failure("Password and confirm password must match exactly.");
        }

        if (repository.existsByEmail(request.getEmail())) {
            return ValidationResult.failure("An account with this email already exists.");
        }

        return ValidationResult.success();
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
