package com.qualle.truegain.client.api;

public class ConfirmRegistration {

    private int code;
    private String token;

    public ConfirmRegistration() {
    }

    public ConfirmRegistration(int code, String token) {
        this.code = code;
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
