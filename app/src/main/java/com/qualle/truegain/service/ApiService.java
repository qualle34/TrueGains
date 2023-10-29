package com.qualle.truegain.service;

import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.api.Exercise;
import com.qualle.truegain.client.api.Record;
import com.qualle.truegain.client.api.Workout;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.model.local.CurrentWorkoutProto;
import com.qualle.truegain.util.DateFormatterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiService {

    @Inject
    public BackendClient client;

    public ApiService() {
        DaggerApplicationComponent.create().inject(this);
    }

    public void saveWorkout(long userId, CurrentWorkoutProto current) {

        List<Exercise> exercises = new ArrayList<>();

        current.getExercises().forEach(e -> {

            Exercise exercise = new Exercise();
            exercise.setId(e.getId());
            exercise.setRecords(e.getRecords().stream()
                    .map(r -> new Record(r.getId(), r.getWeight(), r.getReps()))
                    .collect(Collectors.toList()));

            exercises.add(exercise);
        });

        Workout workout = new Workout();
        workout.setUserId(userId);
        workout.setDate(DateFormatterUtil.toApiDate(current.getDate()));


        workout.setExercises(exercises);


        client.saveWorkout(workout).enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<Workout> call, Response<Workout> response) {
                System.out.println("Saved");
            }

            @Override
            public void onFailure(Call<Workout> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
}
