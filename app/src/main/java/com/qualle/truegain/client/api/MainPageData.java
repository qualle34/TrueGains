package com.qualle.truegain.client.api;

import java.util.List;
import java.util.Map;

public class MainPageData {

    Map<Integer, Integer> workoutPerWeekChartData;
    List<SimpleWorkout> recentWorkouts;
    Map<Float, Float> muscleDistributionChartData;
    List<Chart> recentCharts;

    public MainPageData() {
    }

    public MainPageData(Map<Integer, Integer> workoutPerWeekChartData, List<SimpleWorkout> recentWorkouts, Map<Float, Float> muscleDistributionChartData, List<Chart> recentCharts) {
        this.workoutPerWeekChartData = workoutPerWeekChartData;
        this.recentWorkouts = recentWorkouts;
        this.muscleDistributionChartData = muscleDistributionChartData;
        this.recentCharts = recentCharts;
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

    public Map<Float, Float> getMuscleDistributionChartData() {
        return muscleDistributionChartData;
    }

    public void setMuscleDistributionChartData(Map<Float, Float> muscleDistributionChartData) {
        this.muscleDistributionChartData = muscleDistributionChartData;
    }

    public List<Chart> getRecentCharts() {
        return recentCharts;
    }

    public void setRecentCharts(List<Chart> recentCharts) {
        this.recentCharts = recentCharts;
    }
}
