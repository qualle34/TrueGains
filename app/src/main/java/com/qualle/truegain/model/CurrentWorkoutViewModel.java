package com.qualle.truegain.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qualle.truegain.client.InMemoryBackendClient;
import com.qualle.truegain.model.local.CurrentExerciseProto;
import com.qualle.truegain.model.local.CurrentRecordProto;
import com.qualle.truegain.model.local.CurrentWorkoutProto;
import com.qualle.truegain.client.api.Exercise;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrentWorkoutViewModel extends ViewModel {

    private MutableLiveData<CurrentWorkoutProto> workoutData = new MutableLiveData<>();
    private MutableLiveData<Boolean> exerciseAdded = new MutableLiveData<>();

    public void createEmptyExercise(long exerciseId) {

        if (!workoutData.isInitialized()) {
            throw new NullPointerException("Workout not initialized");
        }

        CurrentWorkoutProto workout = workoutData.getValue();

        Exercise exerciseData = InMemoryBackendClient.getExercise(exerciseId); // todo

        CurrentExerciseProto exercise = new CurrentExerciseProto(exerciseId, exerciseData.getName(), exerciseData.getEquipment(), exerciseData.getDescription(), List.of(new CurrentRecordProto(1, " - ")));

        List<CurrentExerciseProto> exercises = new ArrayList<>(workout.getExercises());
        exercises.add(exercise);
        workout.setExercises(exercises);

        exerciseAdded.setValue(Boolean.TRUE);
        workoutData.setValue(workout);
    }

    public void createEmptyRecord(int exercisePosition) {

        if (!workoutData.isInitialized()) {
            throw new NullPointerException("Workout not initialized");
        }

        CurrentWorkoutProto workout = workoutData.getValue();
        CurrentExerciseProto exercise = workout.getExercises().get(exercisePosition);

        List<CurrentRecordProto> records = new ArrayList<>(exercise.getRecords());
        records.add(new CurrentRecordProto(" - "));
        exercise.setRecords(records);

        workoutData.setValue(workout);
    }

    public void updateRecords(int exercisePosition, Map<Integer, CurrentRecordProto> records) {

        if (!workoutData.isInitialized()) {
            throw new NullPointerException("Workout not initialized");
        }

        CurrentWorkoutProto workout = workoutData.getValue();
        CurrentExerciseProto exercise = workout.getExercises().get(exercisePosition);

        List<CurrentRecordProto> savedRecords = new ArrayList<>(exercise.getRecords());

        records.forEach(savedRecords::set);

        exercise.setRecords(savedRecords);

        workoutData.setValue(workout);
    }

    public MutableLiveData<CurrentWorkoutProto> getWorkout() {
        return workoutData;
    }

    public CurrentExerciseProto getExercise(int position) {
        return workoutData.getValue().getExercises().get(position);
    }

    public CurrentRecordProto getRecord(int exercisePosition, int recordPosition) {
        CurrentExerciseProto exercise = getExercise(exercisePosition);

        if (exercise != null) {
            return exercise.getRecords().get(recordPosition);
        }
        return null;
    }

    public int getExercisesCount() {
        return workoutData.getValue().getExercises().size();
    }

    public int getRecordsCount(int exercisePosition) {
        CurrentExerciseProto currentExercise = workoutData.getValue().getExercises().get(exercisePosition);
        if (currentExercise != null) {
            return currentExercise.getRecords().size();
        }
        return 0;
    }

    public boolean isExerciseAdded() {
        if (!exerciseAdded.isInitialized()) {
            exerciseAdded.setValue(Boolean.FALSE);
            return true;
        }

        return exerciseAdded.getValue().booleanValue();
    }

    public void resetExerciseAdded() {
        exerciseAdded.setValue(Boolean.FALSE);
    }

    public void initialize() {
        CurrentWorkoutProto workout = new CurrentWorkoutProto();
        workout.setDate(LocalDate.now());
        workout.setExercises(new ArrayList<>());
        workoutData.setValue(workout);
    }

    public void setData(CurrentWorkoutProto workout) {
        exerciseAdded.setValue(Boolean.TRUE);
        workoutData.setValue(workout);
    }



}
