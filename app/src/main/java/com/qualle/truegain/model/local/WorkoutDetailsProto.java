package com.qualle.truegain.model.local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailsProto {

    private long id;
    private LocalDate date;

    private int exercisesCount;

    private List<VolumeProto> volumeForBodyParts;
    private List<VolumeProto> volumeForExercises;
    private List<ExerciseDetailsProto> exerciseDetailsList;

    public WorkoutDetailsProto() {
    }

    public WorkoutDetailsProto(long id, LocalDate date, int exercisesCount) {
        this.id = id;
        this.date = date;
        this.exercisesCount = exercisesCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public int getExercisesCount() {
        return exercisesCount;
    }

    public void setExercisesCount(int exercisesCount) {
        this.exercisesCount = exercisesCount;
    }

    public List<VolumeProto> getVolumeForBodyParts() {
        return volumeForBodyParts;
    }

    public void setVolumeForBodyParts(List<VolumeProto> volumeForBodyParts) {
        this.volumeForBodyParts = new ArrayList<>(volumeForBodyParts);
    }

    public List<VolumeProto> getVolumeForExercises() {
        return volumeForExercises;
    }

    public void setVolumeForExercises(List<VolumeProto> volumeForExercises) {
        this.volumeForExercises = new ArrayList<>(volumeForExercises);
    }

    public List<ExerciseDetailsProto> getExerciseDetailsList() {
        return exerciseDetailsList;
    }

    public void setExerciseDetailsList(List<ExerciseDetailsProto> exerciseDetailsList) {
        this.exerciseDetailsList = exerciseDetailsList;
    }
}
