package com.enterprise.auth;

public class RegistrationService {
    private final UserRepository userRepository;

    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ApiResponse register(RegistrationRequest request) {
        ValidationResult validation = RegistrationValidator.validate(request, userRepository);
        if (!validation.isValid()) {
            return new ApiResponse("error", validation.getErrorMessage());
        }

        String passwordHash = PasswordHash.hash(request.getPassword());
        User user = new User(null, request.getUsername(), request.getEmail(), passwordHash);
        userRepository.save(user);

        return new ApiResponse("success", "User registered successfully");
    }
}
