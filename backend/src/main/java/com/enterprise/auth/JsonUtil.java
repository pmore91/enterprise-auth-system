package com.enterprise.auth;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JsonUtil {
    private static final Pattern STRING_PATTERN = Pattern.compile("\\\"([^\\\"]*(?:\\\\.[^\\\"]*)*)\\\"\\s*:\\s*\\\"((?:\\\\.|[^\\\"\\\\])*)\\\"");

    private JsonUtil() {
    }

    public static Map<String, String> parseObject(String json) {
        Map<String, String> values = new LinkedHashMap<>();
        if (json == null || json.isBlank()) {
            return values;
        }

        Matcher matcher = STRING_PATTERN.matcher(json);
        while (matcher.find()) {
            String key = matcher.group(1).replace("\\\"", "\"").replace("\\\\", "\\");
            String value = matcher.group(2).replace("\\\"", "\"").replace("\\\\", "\\");
            values.put(key, value);
        }
        return values;
    }
}
