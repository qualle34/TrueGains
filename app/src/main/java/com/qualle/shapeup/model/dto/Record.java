package com.qualle.shapeup.model.dto;

public class Record {

    private Exercise exercise;
    private int value;
    private String type;

    public Record(){

    }

    public Record(Exercise exercise, int value, String type) {
        this.exercise = exercise;
        this.value = value;
        this.type = type;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
