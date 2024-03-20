package com.terminal.util;

public class Token {
    private static String authToken;

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String authToken) {
        Token.authToken = authToken;
    }
}