package com.enterprise.auth;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.Executors;

public class Server implements AutoCloseable {
    private final HttpServer httpServer;
    private final RegistrationService registrationService;

    public Server(int port, UserRepository userRepository) throws IOException {
        this.registrationService = new RegistrationService(userRepository);
        this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        this.httpServer.createContext("/api/v1/auth/register", this::handleRegister);
        this.httpServer.setExecutor(Executors.newCachedThreadPool());
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(8080, new JdbcUserRepository());
        server.start();
        System.out.println("Registration server started on http://localhost:8080/api/v1/auth/register");
    }

    public void start() {
        httpServer.start();
    }

    public int getPort() {
        return httpServer.getAddress().getPort();
    }

    private void handleRegister(HttpExchange exchange) throws IOException {
        try {
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendJson(exchange, 405, new ApiResponse("error", "Method not allowed"));
                return;
            }

            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> payload = JsonUtil.parseObject(requestBody);

            RegistrationRequest request = new RegistrationRequest();
            request.setUsername(payload.getOrDefault("username", ""));
            request.setEmail(payload.getOrDefault("email", ""));
            request.setPassword(payload.getOrDefault("password", ""));
            request.setConfirmPassword(payload.getOrDefault("confirmPassword", ""));

            ApiResponse response = registrationService.register(request);
            int statusCode = "success".equals(response.getStatus()) ? 200 : 400;
            sendJson(exchange, statusCode, response);
        } catch (Exception e) {
            sendJson(exchange, 400, new ApiResponse("error", "Malformed request payload"));
        } finally {
            exchange.close();
        }
    }

    private void sendJson(HttpExchange exchange, int statusCode, ApiResponse response) throws IOException {
        String json = String.format(
                "{\"status\":\"%s\",\"message\":\"%s\"}",
                escapeJson(response.getStatus()),
                escapeJson(response.getMessage()));

        byte[] body = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, body.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(body);
        }
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    @Override
    public void close() {
        httpServer.stop(0);
    }
}
