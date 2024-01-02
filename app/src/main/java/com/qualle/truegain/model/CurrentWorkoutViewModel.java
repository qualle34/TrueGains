package com.qualle.truegain.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qualle.truegain.client.api.Exercise;
import com.qualle.truegain.client.api.Record;
import com.qualle.truegain.client.api.Workout;
import com.qualle.truegain.util.NumberValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrentWorkoutViewModel extends ViewModel {

    private MutableLiveData<Workout> workoutData = new MutableLiveData<>();
    private MutableLiveData<Boolean> exerciseAdded = new MutableLiveData<>();

    public void createEmptyExercise(Exercise exercise) {

        if (!workoutData.isInitialized()) {
            workoutData.setValue(new Workout());
        }

        List<Record> currentRecords;

        if (exercise.getRecords() != null) {

            currentRecords = exercise.getRecords().stream()
                    .peek(r -> {
                        r.setPrevious(NumberValueFormatter.toDisplayValue(r.getWeight()) + " kg x" + r.getReps());
                        r.setReps(0);
                        r.setId(0);
                    })
                    .collect(Collectors.toList());
        } else {
            currentRecords = List.of(new Record(" - "));
        }

        exercise.setRecords(currentRecords);

        Workout workout = workoutData.getValue();
        workout.getExercises().add(exercise);

        exerciseAdded.setValue(Boolean.TRUE);
        workoutData.setValue(workout);
    }

    public void createEmptyRecord(int exercisePosition) {

        if (!workoutData.isInitialized()) {
            workoutData.setValue(new Workout());
        }

        Workout workout = workoutData.getValue();
        Exercise exercise = workout.getExercises().get(exercisePosition);

        List<Record> records = new ArrayList<>(exercise.getRecords());
        records.add(new Record(" - "));
        exercise.setRecords(records);

        workoutData.setValue(workout);
    }

    public void updateRecords(int exercisePosition, Map<Integer, Record> records) {

        if (!workoutData.isInitialized()) {
            workoutData.setValue(new Workout());
        }

        Workout workout = workoutData.getValue();
        Exercise exercise = workout.getExercises().get(exercisePosition);

        List<Record> savedRecords = new ArrayList<>(exercise.getRecords());

        records.forEach(savedRecords::set);

        exercise.setRecords(savedRecords);

        workoutData.setValue(workout);
    }

    public void deleteWorkout() {
        workoutData.setValue(new Workout());
    }

    public void deleteExercise(int exercisePosition) {

        if (!workoutData.isInitialized()) {
            workoutData.setValue(new Workout());
        }

        Workout workout = workoutData.getValue();
        workout.getExercises().remove(exercisePosition);

        workoutData.setValue(workout);
    }

    public void deleteEmptyRecords(int exercisePosition) {

        if (!workoutData.isInitialized()) {
            workoutData.setValue(new Workout());
        }

        Workout workout = workoutData.getValue();
        Exercise exercise = workout.getExercises().get(exercisePosition);

        List<Record> filledRecords = exercise.getRecords().stream()
                .filter(item -> item.getReps() != 0)
                .collect(Collectors.toList());

        exercise.setRecords(filledRecords);

        workoutData.setValue(workout);
    }

    public MutableLiveData<Workout> getWorkout() {
        return workoutData;
    }

    public Exercise getExercise(int position) {
        return workoutData.getValue().getExercises().get(position);
    }

    public int getExercisesCount() {
        return workoutData.getValue().getExercises().size();
    }

    public int getRecordsCount(int exercisePosition) {
        Exercise currentExercise = workoutData.getValue().getExercises().get(exercisePosition);
        if (currentExercise != null) {
            return currentExercise.getRecords().size();
        }
        return 0;
    }

    public boolean isAvailable() {
        if (!exerciseAdded.isInitialized()) {
            exerciseAdded.setValue(Boolean.FALSE);
            return true;
        }

        return exerciseAdded.getValue().booleanValue();
    }

    public void resetExerciseAdded() {
        exerciseAdded.setValue(Boolean.FALSE);
    }

    public void setApiWorkout(Workout workout) {
        workoutData.setValue(workout);
        exerciseAdded.setValue(Boolean.TRUE);
    }

    public Workout getApiWorkout() {
        return workoutData.getValue();
    }
}
