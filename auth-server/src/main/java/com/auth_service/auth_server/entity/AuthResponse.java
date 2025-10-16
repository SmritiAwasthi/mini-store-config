package com.auth_service.auth_server.entity;

public class AuthResponse {
    private String token;

    public AuthResponse() {}
    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
