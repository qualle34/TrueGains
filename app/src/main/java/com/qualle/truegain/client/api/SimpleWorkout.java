package com.qualle.truegain.client.api;

import java.io.Serializable;

public class SimpleWorkout implements Serializable {

    private long id;
    private String date;
    private long userId;
    private int exerciseCount;

    public SimpleWorkout() {
    }

    public SimpleWorkout(long id, String date, long userId, int exerciseCount) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.exerciseCount = exerciseCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getExerciseCount() {
        return exerciseCount;
    }

    public void setExerciseCount(int exerciseCount) {
        this.exerciseCount = exerciseCount;
    }
}
