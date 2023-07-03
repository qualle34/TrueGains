package com.qualle.shapeup.client.api;

public class RecordSummary {

    private Exercise exercise;
    private int count;

    public RecordSummary(Exercise exercise, int count) {
        this.exercise = exercise;
        this.count = count;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
