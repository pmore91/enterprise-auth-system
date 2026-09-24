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
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port provided, defaulting to 8080");
            }
        }

        Server server = new Server(port, new JdbcUserRepository());
        server.start();
        System.out.println("Registration server started on http://localhost:" + port + "/api/v1/auth/register");
    }

    public void start() {
        httpServer.start();
    }

    public int getPort() {
        return httpServer.getAddress().getPort();
    }

    private void handleRegister(HttpExchange exchange) throws IOException {
        // Explicitly handle browser CORS security checklist pre-flight options queries
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");
            exchange.sendResponseHeaders(204, -1);
            exchange.close();
            return;
        }

        try {
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendJson(exchange, 405, new ApiResponse("error", "Method not allowed"));
                return;
            }

            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> payload = JsonUtil.parseObject(requestBody);

            RegistrationRequest request = new RegistrationRequest();
            request.setUsername(payload.getOrDefault("username", "").trim());
            request.setEmail(payload.getOrDefault("email", "").trim());
            request.setPassword(payload.getOrDefault("password", ""));
            request.setConfirmPassword(payload.getOrDefault("confirmPassword", ""));

            ApiResponse response = registrationService.register(request);
            int statusCode = "success".equals(response.getStatus()) ? 200 : 400;
            sendJson(exchange, statusCode, response);
        } catch (Exception e) {
            e.printStackTrace();
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
        
        // Injected core enterprise CORS headers straight into the active HTTP output channel variables
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");
        
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
