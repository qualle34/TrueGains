package com.qualle.truegain.model.local;

import java.time.Instant;

public class LocalUser {

    private long id;
    private String accessToken;
    private Instant accessTokenExpiredAt;
    private String refreshToken;
    private Instant refreshTokenExpiredAt;

    public LocalUser() {
    }

    public LocalUser(long id, String accessToken, String refreshToken) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public LocalUser(long id, String accessToken, Instant accessTokenExpiredAt, String refreshToken, Instant refreshTokenExpiredAt) {
        this.id = id;
        this.accessToken = accessToken;
        this.accessTokenExpiredAt = accessTokenExpiredAt;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiredAt = refreshTokenExpiredAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Instant getAccessTokenExpiredAt() {
        return accessTokenExpiredAt;
    }

    public void setAccessTokenExpiredAt(Instant accessTokenExpiredAt) {
        this.accessTokenExpiredAt = accessTokenExpiredAt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Instant getRefreshTokenExpiredAt() {
        return refreshTokenExpiredAt;
    }

    public void setRefreshTokenExpiredAt(Instant refreshTokenExpiredAt) {
        this.refreshTokenExpiredAt = refreshTokenExpiredAt;
    }
}
