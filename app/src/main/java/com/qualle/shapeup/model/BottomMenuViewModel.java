package com.qualle.shapeup.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qualle.shapeup.client.api.Exercise;
import com.qualle.shapeup.client.api.Workout;

public class BottomMenuViewModel extends ViewModel {

    private MutableLiveData<Exercise> exerciseData = new MutableLiveData<>();

    public void selectExercise(long exerciseId) {

        Exercise exercise = new Exercise();
        exercise.setId(exerciseId);
        exerciseData.setValue(exercise);
    }

    public MutableLiveData<Exercise> getExercise() {
        return exerciseData;
    }
}
