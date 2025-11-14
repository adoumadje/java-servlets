package com.adoumadje.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Cryptographer {
    public static String encode(String plainText) {
        return Base64.getEncoder().encodeToString(plainText.getBytes(StandardCharsets.UTF_8));
    }
}
