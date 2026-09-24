package com.enterprise.auth;

public class AuthController {
    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public ApiResponse register(RegistrationRequest request) {
        return registrationService.register(request);
    }
}
