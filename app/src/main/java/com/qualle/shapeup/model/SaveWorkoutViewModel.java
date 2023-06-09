package com.qualle.shapeup.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qualle.shapeup.model.dto.Record;
import com.qualle.shapeup.model.dto.Workout;

import java.time.LocalDateTime;
import java.util.List;

public class SaveWorkoutViewModel extends ViewModel {

    private MutableLiveData<Workout> workoutData = new MutableLiveData<>();

    public void addRecord(Record record) {

        if (!workoutData.isInitialized()) {
            Workout workout = buildWorkout();
            workout.setRecords(List.of(record));
            workoutData.setValue(workout);
        }

        workoutData.getValue().getRecords().add(record);
    }

    public MutableLiveData<Workout> getWorkout() {
        return workoutData;
    }

    private Workout buildWorkout() {
        Workout workout = new Workout();
        workout.setDate(LocalDateTime.now());
        return workout;
    }
}
