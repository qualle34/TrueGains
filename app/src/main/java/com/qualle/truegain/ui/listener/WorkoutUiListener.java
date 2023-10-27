package com.qualle.truegain.ui.listener;

import com.qualle.truegain.client.api.SimpleWorkout;
import com.qualle.truegain.client.api.Workout;

import java.util.List;

public interface WorkoutUiListener {

    void onWorkoutAvailable(List<SimpleWorkout> workouts);
}
