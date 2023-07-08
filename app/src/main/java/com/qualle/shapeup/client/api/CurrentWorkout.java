package com.qualle.shapeup.client.api;

import java.util.List;

public class CurrentWorkout {

    private String date;
    private List<CurrentExercise> exercises;

    public CurrentWorkout() {
    }

    public CurrentWorkout(String date, List<CurrentExercise> exercises) {
        this.date = date;
        this.exercises = exercises;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CurrentExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<CurrentExercise> exercises) {
        this.exercises = exercises;
    }
}
