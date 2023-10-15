package com.qualle.truegain.model.local;

public class CurrentRecordProto {

    private long id;
    private String previous;
    private float weight;
    private int reps;

    public CurrentRecordProto() {
    }

    public CurrentRecordProto(String previous) {
        this.previous = previous;
    }

    public CurrentRecordProto(long id, String previous) {
        this.id = id;
        this.previous = previous;
    }

    public CurrentRecordProto(long id, String previous, float weight, int reps) {
        this.id = id;
        this.previous = previous;
        this.weight = weight;
        this.reps = reps;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
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
}
