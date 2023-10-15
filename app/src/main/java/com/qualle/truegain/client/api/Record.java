package com.qualle.truegain.client.api;

public class Record {

    private Exercise exercise;
    private float value;
    private int reps;

    public Record() {

    }

    public Record(Exercise exercise, float value, int reps) {
        this.exercise = exercise;
        this.value = value;
        this.reps = reps;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
