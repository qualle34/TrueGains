package com.qualle.truegain.client.api;

public class TokenAuthentication {

    private String token;

    public TokenAuthentication() {
    }

    public TokenAuthentication(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
