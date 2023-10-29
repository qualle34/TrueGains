package com.qualle.truegain.client.api;

public class Record {

    private long id;
    private long exerciseId;
    private float weight;
    private int reps;
    private String previous;

    public Record() {
    }

    public Record(String previous) {
        this.previous = previous;
    }

    public Record(long id, float weight, int reps) {
        this.id = id;
        this.weight = weight;
        this.reps = reps;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }
}
