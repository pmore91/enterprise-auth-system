package com.enterprise.auth;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistrationServerTest {
    @Test
    void registerViaHttpEndpoint() throws IOException, InterruptedException {
        try (Server server = new Server(0, new InMemoryUserRepository())) {
            server.start();
            HttpClient client = HttpClient.newHttpClient();
            String json = "{\"username\":\"alice\",\"email\":\"alice@example.com\",\"password\":\"StrongPass123!\",\"confirmPassword\":\"StrongPass123!\"}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + server.getPort() + "/api/v1/auth/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("\"status\":\"success\""));
        }
    }
}
