package com.qualle.truegain.client.api;

import java.util.List;
import java.util.Map;

public class MainPageData {

    private List<WorkoutPerWeek> workoutPerWeekChartData;
    private List<SimpleWorkout> recentWorkouts;
    private MuscleDistributionChart muscleDistributionChartData;
    private List<SimpleExercise> frequentExercises;

    public MainPageData() {
    }

    public MainPageData(List<WorkoutPerWeek> workoutPerWeekChartData, List<SimpleWorkout> recentWorkouts, MuscleDistributionChart muscleDistributionChartData, List<SimpleExercise> frequentExercises) {
        this.workoutPerWeekChartData = workoutPerWeekChartData;
        this.recentWorkouts = recentWorkouts;
        this.muscleDistributionChartData = muscleDistributionChartData;
        this.frequentExercises = frequentExercises;
    }

    public List<WorkoutPerWeek> getWorkoutPerWeekChartData() {
        return workoutPerWeekChartData;
    }

    public void setWorkoutPerWeekChartData(List<WorkoutPerWeek> workoutPerWeekChartData) {
        this.workoutPerWeekChartData = workoutPerWeekChartData;
    }

    public List<SimpleWorkout> getRecentWorkouts() {
        return recentWorkouts;
    }

    public void setRecentWorkouts(List<SimpleWorkout> recentWorkouts) {
        this.recentWorkouts = recentWorkouts;
    }

    public MuscleDistributionChart getMuscleDistributionChartData() {
        return muscleDistributionChartData;
    }

    public void setMuscleDistributionChartData(MuscleDistributionChart muscleDistributionChartData) {
        this.muscleDistributionChartData = muscleDistributionChartData;
    }

    public List<SimpleExercise> getFrequentExercises() {
        return frequentExercises;
    }

    public void setFrequentExercises(List<SimpleExercise> frequentExercises) {
        this.frequentExercises = frequentExercises;
    }
}
