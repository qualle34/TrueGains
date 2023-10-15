package com.qualle.truegain.model.local;

import java.time.LocalDate;
import java.util.List;

public class CurrentWorkoutProto {

    private LocalDate date;
    private List<CurrentExerciseProto> exercises;

    public CurrentWorkoutProto() {
    }

    public CurrentWorkoutProto(LocalDate date, List<CurrentExerciseProto> exercises) {
        this.date = date;
        this.exercises = exercises;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<CurrentExerciseProto> getExercises() {
        return exercises;
    }

    public void setExercises(List<CurrentExerciseProto> exercises) {
        this.exercises = exercises;
    }
}
