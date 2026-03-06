package com.katherina.nuevospa.taskmanagerapi.dto;

public record LoginResponse(
        String token,
        String tokenType,
        String email
) {
    public LoginResponse(String token, String email) {
        this(token, "Bearer", email);
    }
}
