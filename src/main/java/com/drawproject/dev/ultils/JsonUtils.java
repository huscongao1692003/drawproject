package com.drawproject.dev.ultils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    public static <T> T getJson(String object, Class<T> objectClass) {
        T jsonObject = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonObject = objectMapper.readValue(object, objectClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
