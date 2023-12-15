package com.qualle.truegain.service;

import android.content.Context;

import com.google.gson.Gson;
import com.qualle.truegain.client.api.Token;
import com.qualle.truegain.client.api.TokenClaims;
import com.qualle.truegain.model.UserData;
import com.qualle.truegain.model.WorkoutData;
import com.qualle.truegain.model.local.CurrentExerciseProto;
import com.qualle.truegain.model.local.CurrentRecordProto;
import com.qualle.truegain.model.local.CurrentWorkoutProto;
import com.qualle.truegain.model.local.LocalUser;
import com.qualle.truegain.repository.LocalRepository;
import com.qualle.truegain.util.DateFormatterUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class LocalService {

    private static volatile LocalService instance;

    private final LocalRepository repository;

    private volatile LocalUser cachedUser;

    private final Gson gson = new Gson();

    private LocalService(LocalRepository repository) {
        this.repository = repository;
    }

    public static LocalService getInstance(Context context) {
        LocalService localInstance = instance;
        if (localInstance == null) {
            synchronized (LocalService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new LocalService(new LocalRepository(context));
                }
            }
        }
        return localInstance;
    }

    public String getAuthorizationHeader() {
        return "Bearer " + getUser().getAccessToken();
    }

    public LocalUser getUser() {

        if (cachedUser != null) {
            return cachedUser;
        }

        UserData data = repository.getUser();

        return new LocalUser(
                data.getId(),
                data.getAccessToken(),
                Instant.ofEpochSecond(data.getAccessTokenExpiredAt()),
                data.getRefreshToken(),
                Instant.ofEpochSecond(data.getRefreshTokenExpiredAt())
        );
    }

    public String getTemporaryToken() {
        UserData data = repository.getUser();

        return data.getTemporaryToken();
    }


    public void saveTemporaryToken(String token) {
        UserData data = UserData.newBuilder()
                .setTemporaryToken(token)
                .build();

        repository.saveUser(data);
    }


    public void saveAuthToken(Token token) {

        byte[] accessBytes = Base64.getDecoder().decode(token.getAccessToken().split("\\.")[1]);
        TokenClaims accessClaims = gson.fromJson(new String(accessBytes), TokenClaims.class);

        byte[] refreshBytes = Base64.getDecoder().decode(token.getRefreshToken().split("\\.")[1]);
        TokenClaims refreshClaims = gson.fromJson(new String(refreshBytes), TokenClaims.class);

        UserData data = UserData.newBuilder()
                .setId(accessClaims.getUid())
                .setAccessToken(token.getAccessToken())
                .setAccessTokenExpiredAt(accessClaims.getExp())
                .setRefreshToken(token.getRefreshToken())
                .setRefreshTokenExpiredAt(refreshClaims.getExp())
                .build();

        cachedUser = new LocalUser(
                data.getId(),
                data.getAccessToken(),
                Instant.ofEpochSecond(data.getAccessTokenExpiredAt()),
                data.getRefreshToken(),
                Instant.ofEpochSecond(data.getRefreshTokenExpiredAt())
        );
        repository.saveUser(data);
    }

    public boolean isCurrentWorkoutStarted() {
        return repository.getCurrentWorkout() != null;
    }

    public void clearAuthentication() {
        UserData data = repository.getUser();

        UserData newData = UserData.newBuilder()
                .setId(data.getId())
                .build();

        repository.saveUser(newData);
    }
}
