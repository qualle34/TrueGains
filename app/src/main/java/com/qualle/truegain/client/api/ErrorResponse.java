package com.qualle.truegain.client.api;

import java.util.Map;

public class ErrorResponse {

    private ErrorType type;
    private String message;
    private Map<String, String> additional;

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorType type, String message, Map<String, String> additional) {
        this.type = type;
        this.message = message;
        this.additional = additional;
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getAdditional() {
        return additional;
    }

    public void setAdditional(Map<String, String> additional) {
        this.additional = additional;
    }
}
