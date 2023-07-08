package com.qualle.shapeup.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qualle.shapeup.client.InMemoryBackendClient;
import com.qualle.shapeup.client.api.CurrentExercise;
import com.qualle.shapeup.client.api.CurrentRecord;
import com.qualle.shapeup.client.api.CurrentWorkout;
import com.qualle.shapeup.client.api.Exercise;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrentWorkoutViewModel extends ViewModel {

    private MutableLiveData<CurrentWorkout> workoutData = new MutableLiveData<>();

    public void createEmptyExercise(long exerciseId) {

        if (!workoutData.isInitialized()) {
            throw new NullPointerException("Workout not initialized");
        }

        CurrentWorkout workout = workoutData.getValue();

        Exercise exerciseData = InMemoryBackendClient.getExercise(exerciseId); // todo

        CurrentExercise exercise = new CurrentExercise(exerciseId, exerciseData.getName(), exerciseData.getDescription(), List.of(new CurrentRecord(1, " - ")));

        List<CurrentExercise> exercises = new ArrayList<>(workout.getExercises());
        exercises.add(exercise);
        workout.setExercises(exercises);

        workoutData.setValue(workout);
    }

    public void createEmptyRecord(int exercisePosition) {

        if (!workoutData.isInitialized()) {
            throw new NullPointerException("Workout not initialized");
        }

        CurrentWorkout workout = workoutData.getValue();
        CurrentExercise exercise = workout.getExercises().get(exercisePosition);

        List<CurrentRecord> records = new ArrayList<>(exercise.getRecords());
        records.add(new CurrentRecord());
        exercise.setRecords(records);

        workoutData.setValue(workout);
    }

    public void updateRecords(int exercisePosition, Map<Integer, CurrentRecord> records) {

        if (!workoutData.isInitialized()) {
            throw new NullPointerException("Workout not initialized");
        }

        CurrentWorkout workout = workoutData.getValue();
        CurrentExercise exercise = workout.getExercises().get(exercisePosition);

        List<CurrentRecord> savedRecords = new ArrayList<>(exercise.getRecords());

        records.forEach(savedRecords::set);

        exercise.setRecords(savedRecords);

        workoutData.setValue(workout);
    }

    public void updateRecord(int exercisePosition, CurrentRecord record) {

        if (!workoutData.isInitialized()) {
            throw new NullPointerException("Workout not initialized");
        }

        CurrentWorkout workout = workoutData.getValue();
        CurrentExercise exercise = workout.getExercises().get(exercisePosition);
// todo
        List<CurrentRecord> records = new ArrayList<>(exercise.getRecords());
//        records.set(record.getPosition(), record);
        exercise.setRecords(records);

        workoutData.setValue(workout);
    }

    public MutableLiveData<CurrentWorkout> getWorkout() {
        return workoutData;
    }

    public CurrentExercise getExercise(int position) {
        return workoutData.getValue().getExercises().get(position);
    }

    public CurrentRecord getRecord(int exercisePosition, int recordPosition) {
        CurrentExercise exercise = getExercise(exercisePosition);

        if (exercise != null) {
            return exercise.getRecords().get(recordPosition);
        }
        return null;
    }

    public int getExercisesCount() {
        return workoutData.getValue().getExercises().size();
    }

    public int getRecordsCount(int exercisePosition) {
        CurrentExercise currentExercise = workoutData.getValue().getExercises().get(exercisePosition);
        if (currentExercise != null) {
            return currentExercise.getRecords().size();
        }
        return 0;
    }


    public void initialize() {
        CurrentWorkout workout = new CurrentWorkout();
        workout.setDate(LocalDateTime.now().toString());
        workout.setExercises(new ArrayList<>());
        workoutData.setValue(workout);
    }

    public void setData(CurrentWorkout workout) {
        workoutData.setValue(workout);
    }
}
