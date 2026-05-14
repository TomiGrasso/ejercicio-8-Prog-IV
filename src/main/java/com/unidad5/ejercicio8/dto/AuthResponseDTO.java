package com.unidad5.ejercicio8.dto;

public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer";
    private long expiresIn;
    private String username;

    public AuthResponseDTO(String accessToken, long expiresIn, String username) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public String getTokenType() {
        return tokenType;
    }
    public long getExpiresIn() {
        return expiresIn;
    }
    public String getUsername() {
        return username;
    }
}
