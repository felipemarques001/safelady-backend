package com.uast.ms_auth.utils;

public class StringUtils {

    public static String toCamelCase(String value) {
        String[] parts = value.split(" ");
        StringBuilder result = new StringBuilder(parts[0].toLowerCase());

        for (int i = 1; i < parts.length; i++) {
            result.append(parts[i].substring(0, 1).toUpperCase());
            result.append(parts[i].substring(1).toLowerCase());
        }

        return result.toString();
    }
}
