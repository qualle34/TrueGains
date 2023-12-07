package com.qualle.truegain.model.exception;

public class ApiAuthenticationException extends RuntimeException {

    public ApiAuthenticationException() {
    }

    public ApiAuthenticationException(String message) {
        super(message);
    }

    public ApiAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
