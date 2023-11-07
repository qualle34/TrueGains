package com.qualle.truegain.service;

public interface AuthenticationHandler {

    boolean isAuthenticationRequired();

    boolean isRefreshRequired();

    void refresh();

    void logout();
}
