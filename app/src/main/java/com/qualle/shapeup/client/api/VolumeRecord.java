package com.qualle.shapeup.client.api;

public class VolumeRecord {

    private String exercise;
    private int value;

    public VolumeRecord() {

    }

    public VolumeRecord(String exercise, int value) {
        this.exercise = exercise;
        this.value = value;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
