package com.qualle.truegain.service;

import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.api.Token;
import com.qualle.truegain.client.api.TokenAuthentication;
import com.qualle.truegain.model.local.LocalUser;
import com.qualle.truegain.util.DateFormatterUtil;

import java.time.LocalDateTime;

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

    @Override
    public boolean isAuthenticationRequired() {
        LocalUser user = service.getUser();

        if (user == null || user.getId() == 0) {
            return true;
        }

        if (user.getRefreshToken() == null
                || user.getAccessToken() == null
                || user.getAccessTokenExpiredAt() == null
                || user.getRefreshTokenExpiredAt() == null) {
            return true;
        }

        // if >1 min before token expired
        return LocalDateTime.now().minusMinutes(1)
                .isAfter(user.getRefreshTokenExpiredAt());
    }

    @Override
    public boolean isRefreshRequired() {
        LocalUser user = service.getUser();

        if (isAuthenticationRequired()) {
            return true; // todo
        }

        // if >1 min before token expired
        return LocalDateTime.now().minusMinutes(1)
                .isAfter(user.getAccessTokenExpiredAt());
    }

    @Override
    public void refresh() {

        LocalUser user = service.getUser();

        TokenAuthentication authentication = new TokenAuthentication(user.getRefreshToken());


        client.refresh(authentication).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                Token token = response.body();

                if (response.isSuccessful() && token != null) {

                    LocalUser user = new LocalUser();
                    user.setAccessToken(token.getAccessToken());
                    user.setAccessTokenExpiredAt(DateFormatterUtil.fromApiDate(token.getAccessTokenExpiredAt()));
                    user.setRefreshToken(token.getRefreshToken());
                    user.setRefreshTokenExpiredAt(DateFormatterUtil.fromApiDate(token.getRefreshTokenExpiredAt()));
                    service.saveUser(user);

                } else {
                    service.clearAuthentication();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void logout() {
        LocalUser user = service.getUser();

        TokenAuthentication authentication = new TokenAuthentication(user.getRefreshToken());

        client.logout(authentication).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                service.clearAuthentication();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                service.clearAuthentication();
                t.printStackTrace();
            }
        });
    }
}
