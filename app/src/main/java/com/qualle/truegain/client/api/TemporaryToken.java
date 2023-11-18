package com.qualle.truegain.client.api;

public class TemporaryToken {

    private String token;

    public TemporaryToken() {
    }

    public TemporaryToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
