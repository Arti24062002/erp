package com.erp.erp.security;

import java.nio.charset.StandardCharsets;

import java.util.Base64;

public class Base64Util {
 
    public static String encode(String input) {
        byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    // Decode a Base64 encoded string (not used in this scenario)
    public static String decode(String input) {
        byte[] decodedBytes = Base64.getDecoder().decode(input.getBytes(StandardCharsets.UTF_8));
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
   
}
