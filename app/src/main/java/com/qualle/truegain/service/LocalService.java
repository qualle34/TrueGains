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

    private LocalUser cachedUser;

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

    public void saveWorkout(CurrentWorkoutProto currentWorkout) {
        List<WorkoutData.ExerciseData> exerciseDataList = new ArrayList<>();

        currentWorkout.getExercises().forEach(exercise -> {
            List<WorkoutData.ExerciseData.RecordData> recordDataList = new ArrayList<>();

            exercise.getRecords().forEach(record -> {
                WorkoutData.ExerciseData.RecordData recordData = WorkoutData.ExerciseData.RecordData.newBuilder()
                        .setId(record.getId())
                        .setPrevious(record.getPrevious())
                        .setWeight(record.getWeight())
                        .setReps(record.getReps())
                        .build();

                recordDataList.add(recordData);
            });

            exerciseDataList.add(WorkoutData.ExerciseData.newBuilder()
                    .setId(exercise.getId())
                    .setName(exercise.getName())
                    .setEquipment(exercise.getEquipment())
                    .setImage(exercise.getImageLink())
                    .addAllRecords(recordDataList)
                    .build());
        });

        WorkoutData workoutData = WorkoutData.newBuilder()
                .addAllExercises(exerciseDataList)
                .setDate(currentWorkout.getDate().toEpochSecond(ZoneOffset.UTC)) // todo
                .setId(currentWorkout.getId())
                .build();

        repository.saveWorkout(workoutData);
    }

    public CurrentWorkoutProto getCurrentWorkout() {
        WorkoutData workout = repository.getCurrentWorkout();

        if (workout == null) {
            return new CurrentWorkoutProto(LocalDateTime.now(), new ArrayList<>());
        }

        List<CurrentExerciseProto> exerciseList = new ArrayList<>();

        if (workout.getExercisesList() != null) {

            workout.getExercisesList().forEach(exercise -> {
                List<CurrentRecordProto> recordList = new ArrayList<>();

                exercise.getRecordsList().forEach(record -> {
                    recordList.add(new CurrentRecordProto(record.getId(), record.getPrevious(), record.getWeight(), record.getReps()));
                });

                exerciseList.add(new CurrentExerciseProto(exercise.getId(), exercise.getName(), exercise.getEquipment(), exercise.getImage(), recordList));
            });
        }

        return new CurrentWorkoutProto(LocalDateTime.ofEpochSecond(workout.getDate(), 0, ZoneOffset.UTC), exerciseList);
    }

    public String getAuthorizationHeader() {
        UserData data = repository.getUser();
        return "Bearer " + data.getAccessToken();
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
