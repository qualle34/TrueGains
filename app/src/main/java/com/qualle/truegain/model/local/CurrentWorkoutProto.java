package com.qualle.truegain.model.local;

import java.time.LocalDateTime;
import java.util.List;

public class CurrentWorkoutProto {

    private long id;
    private LocalDateTime date;
    private List<CurrentExerciseProto> exercises;

    public CurrentWorkoutProto() {
    }

    public CurrentWorkoutProto(LocalDateTime date, List<CurrentExerciseProto> exercises) {
        this.date = date;
        this.exercises = exercises;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<CurrentExerciseProto> getExercises() {
        return exercises;
    }

    public void setExercises(List<CurrentExerciseProto> exercises) {
        this.exercises = exercises;
    }
}
