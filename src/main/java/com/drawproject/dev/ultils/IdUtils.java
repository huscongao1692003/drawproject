package com.drawproject.dev.ultils;

import java.util.UUID;

public class IdUtils {

    public static String generateRandomID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generateNumId() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String generateCode(int id, String object) {
        return object.substring(0, 2) + id + generateNumId();
    }

}
