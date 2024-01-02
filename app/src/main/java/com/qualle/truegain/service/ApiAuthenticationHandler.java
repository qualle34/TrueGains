package com.qualle.truegain.service;

import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.api.LoginPasswordAuthentication;
import com.qualle.truegain.client.api.Token;
import com.qualle.truegain.client.api.TokenAuthentication;
import com.qualle.truegain.model.local.LocalUser;

import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiAuthenticationHandler implements AuthenticationHandler {

    private final BackendClient client;
    private final LocalService service;

    public ApiAuthenticationHandler(BackendClient client, LocalService service) {
        this.client = client;
        this.service = service;
    }

    public void holdAuthentication() {
        LocalUser user = service.getUser();

        if (user == null) {
            throw new RuntimeException("Authentication required, Unable load user from memory");
        }

        if (user.getAccessToken() != null && Instant.now().isBefore(user.getAccessTokenExpiredAt())) {
            return;
        }

        if (user.getRefreshToken() != null && Instant.now().isBefore(user.getRefreshTokenExpiredAt())) {
            refresh(user);
            return;
        }

        throw new RuntimeException("Authentication required, we cant hold session");
    }

    @Override
    public void login(LoginPasswordAuthentication authentication) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<Token> callableTask = () -> {
            Response<Token> response = client.login(authentication).execute();

            if (!response.isSuccessful()) {
                throw new RuntimeException("Unable to login, response code: " + response.code());
            }

            return response.body();
        };

        try {
            Future<Token> future = executorService.submit(callableTask);
            Token token = future.get(20, TimeUnit.SECONDS);

            if (token != null) {
                service.saveAuthToken(token);
            }

        } catch (Exception e) {
            service.clearAuthentication();
        } finally {
            executorService.shutdown();
        }
    }

    @Override
    public void refresh() {
        refresh(service.getUser());
    }

    private void refresh(LocalUser user) {
        TokenAuthentication authentication = new TokenAuthentication(user.getRefreshToken());

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<Token> callableTask = () -> {
            Response<Token> response = client.refresh(authentication).execute();

            if (!response.isSuccessful()) {
                throw new RuntimeException("Unable to refresh token, response code: " + response.code());
            }

            return response.body();
        };

        try {
            Future<Token> future = executorService.submit(callableTask);
            Token result = future.get(20, TimeUnit.SECONDS);

            if (result != null) {
                service.saveAuthToken(result);
            }

        } catch (Exception e) {
            throw new RuntimeException("Unable to refresh token", e);
        } finally {
            executorService.shutdown();
        }
    }

    @Override
    public void logout() {
        LocalUser user = service.getUser();

        TokenAuthentication authentication = new TokenAuthentication(user.getRefreshToken());

        client.logout(authentication).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });

        service.clearAuthentication();
    }

}
