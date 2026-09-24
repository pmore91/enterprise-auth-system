package com.enterprise.auth;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> usersByEmail = new HashMap<>();

    @Override
    public boolean existsByEmail(String email) {
        return usersByEmail.containsKey(email.toLowerCase());
    }

    @Override
    public User save(User user) {
        usersByEmail.put(user.getEmail().toLowerCase(), user);
        return user;
    }
}
