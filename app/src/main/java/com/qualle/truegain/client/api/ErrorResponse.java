package com.qualle.truegain.client.api;

import java.util.Collection;
import java.util.Map;

public class ErrorResponse {

    private String type;
    private String message;
    private Map<String, String> additional;
    private Collection<String> stack;

    public ErrorResponse() {
    }

    public ErrorResponse(String type, String message, Map<String, String> additional, Collection<String> stack) {
        this.type = type;
        this.message = message;
        this.additional = additional;
        this.stack = stack;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public Collection<String> getStack() {
        return stack;
    }

    public void setStack(Collection<String> stack) {
        this.stack = stack;
    }
}
