package com.qualle.truegain.model.local;

public class SimpleWorkoutProto {

    private long id;
    private String date;
    private int exercisesCount;
    private int achievementsCount;

    public SimpleWorkoutProto() {

    }

    public SimpleWorkoutProto(String date, int achievementsCount) {
        this.date = date;
        this.achievementsCount = achievementsCount;
    }

    public SimpleWorkoutProto(String date, int exercisesCount, int achievementsCount) {
        this.date = date;
        this.exercisesCount = exercisesCount;
        this.achievementsCount = achievementsCount;
    }

    public SimpleWorkoutProto(long id, String date, int exercisesCount, int achievementsCount) {
        this.id = id;
        this.date = date;
        this.exercisesCount = exercisesCount;
        this.achievementsCount = achievementsCount;
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

    public int getExercisesCount() {
        return exercisesCount;
    }

    public void setExercisesCount(int exercisesCount) {
        this.exercisesCount = exercisesCount;
    }

    public int getAchievementsCount() {
        return achievementsCount;
    }

    public void setAchievementsCount(int achievementsCount) {
        this.achievementsCount = achievementsCount;
    }
}
