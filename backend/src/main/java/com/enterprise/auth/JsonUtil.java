package com.enterprise.auth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtil() {
    }

    public static Map<String, String> parseObject(String json) {
        if (json == null || json.isBlank()) {
            return Collections.emptyMap();
        }

        try {
            Map<String, Object> rawValues = OBJECT_MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {});
            Map<String, String> values = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : rawValues.entrySet()) {
                Object value = entry.getValue();
                values.put(entry.getKey(), value == null ? "" : String.valueOf(value));
            }
            return values;
        } catch (IOException e) {
            throw new IllegalArgumentException("Malformed request payload", e);
        }
    }
}
