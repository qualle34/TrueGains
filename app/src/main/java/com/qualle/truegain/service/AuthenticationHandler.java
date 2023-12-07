package com.qualle.truegain.service;

import com.qualle.truegain.client.api.LoginPasswordAuthentication;

public interface AuthenticationHandler {

    void holdAuthentication();

    void login(LoginPasswordAuthentication authentication);

    void refresh();

    void logout();
}
