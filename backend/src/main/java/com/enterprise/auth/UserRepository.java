package com.enterprise.auth;

public interface UserRepository {
    boolean existsByEmail(String email);
    User save(User user);
}
