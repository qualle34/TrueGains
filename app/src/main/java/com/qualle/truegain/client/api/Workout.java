package com.qualle.truegain.client.api;

import java.time.LocalDateTime;
import java.util.List;

public class Workout {

    private long id;
    private long userId;
    private String date;
    private List<Exercise> exercises;
    private List<WorkoutVolume> volumeForExercises;
    private List<WorkoutVolume> volumeForBodyParts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<WorkoutVolume> getVolumeForExercises() {
        return volumeForExercises;
    }

    public void setVolumeForExercises(List<WorkoutVolume> volumeForExercises) {
        this.volumeForExercises = volumeForExercises;
    }

    public List<WorkoutVolume> getVolumeForBodyParts() {
        return volumeForBodyParts;
    }

    public void setVolumeForBodyParts(List<WorkoutVolume> volumeForBodyParts) {
        this.volumeForBodyParts = volumeForBodyParts;
    }
}
