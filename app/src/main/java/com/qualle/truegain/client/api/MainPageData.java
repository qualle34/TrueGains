package com.qualle.truegain.client.api;

import java.util.List;
import java.util.Map;

public class MainPageData {

    private Map<Integer, Integer> workoutPerWeekChartData;
    private List<SimpleWorkout> recentWorkouts;
    private MuscleDistributionChart muscleDistributionChartData;
    private List<SimpleExercise> frequentExercises;

    public MainPageData() {
    }

    public MainPageData(Map<Integer, Integer> workoutPerWeekChartData, List<SimpleWorkout> recentWorkouts, MuscleDistributionChart muscleDistributionChartData, List<SimpleExercise> frequentExercises) {
        this.workoutPerWeekChartData = workoutPerWeekChartData;
        this.recentWorkouts = recentWorkouts;
        this.muscleDistributionChartData = muscleDistributionChartData;
        this.frequentExercises = frequentExercises;
    }

    public Map<Integer, Integer> getWorkoutPerWeekChartData() {
        return workoutPerWeekChartData;
    }

    public void setWorkoutPerWeekChartData(Map<Integer, Integer> workoutPerWeekChartData) {
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
