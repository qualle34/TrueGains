package com.qualle.truegain.model.local;

import java.io.Serializable;

public class VolumeProto implements Serializable {

    private String exercise;
    private int value;

    public VolumeProto() {

    }

    public VolumeProto(String exercise, int value) {
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
