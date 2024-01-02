package com.qualle.truegain.client.api;

public class WorkoutPerWeek {

    private int day;
    private int week;
    private int count;

    public WorkoutPerWeek() {
    }

    public WorkoutPerWeek(int day, int week, int count) {
        this.day = day;
        this.week = week;
        this.count = count;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
