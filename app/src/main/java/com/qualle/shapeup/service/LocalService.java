package com.qualle.shapeup.service;

import android.content.Context;

import com.qualle.shapeup.client.api.CurrentExercise;
import com.qualle.shapeup.client.api.CurrentRecord;
import com.qualle.shapeup.client.api.CurrentWorkout;
import com.qualle.shapeup.model.datastore.WorkoutData;
import com.qualle.shapeup.repository.LocalRepository;

import java.util.ArrayList;
import java.util.List;

public class LocalService {

    private final LocalRepository repository;
    private static LocalService instance;

    public LocalService(LocalRepository repository) {
        this.repository = repository;
    }

    public static LocalService getInstance(Context context) {
        LocalService localInstance = instance;
        if (localInstance == null) {
            synchronized (LocalService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new LocalService(LocalRepository.getInstance(context));
                }
            }
        }
        return localInstance;
    }

    public void saveWorkout(CurrentWorkout currentWorkout) {
        List<WorkoutData.ExerciseData> exerciseDataList = new ArrayList<>();

        currentWorkout.getExercises().forEach(exercise -> {
            List<WorkoutData.ExerciseData.RecordData> recordDataList = new ArrayList<>();

            exercise.getRecords().forEach(record -> {
                WorkoutData.ExerciseData.RecordData recordData = WorkoutData.ExerciseData.RecordData.newBuilder()
                        .setId(record.getId())
                        .setPrevious(record.getPrevious() == null ? "" : record.getPrevious())
                        .setValue(record.getWeight())
                        .setReps(record.getReps())
                        .build();

                recordDataList.add(recordData);
            });

            exerciseDataList.add(WorkoutData.ExerciseData.newBuilder()
                    .setId(exercise.getId())
                    .setTitle(exercise.getTitle())
                    .setImage(exercise.getImageLink() == null ? "" : exercise.getImageLink())
                    .addAllRecords(recordDataList)
                    .build());
        });


        WorkoutData workoutData = WorkoutData.newBuilder()
                .addAllExercises(exerciseDataList)
                .setDate(currentWorkout.getDate()).build();


        repository.saveWorkout(workoutData);

    }

    public CurrentWorkout getWorkout() {
        WorkoutData workoutData = repository.getWorkout();

        List<CurrentExercise> exerciseList = new ArrayList<>();

        workoutData.getExercisesList().forEach(exercise -> {
            List<CurrentRecord> recordList = new ArrayList<>();

            exercise.getRecordsList().forEach(record -> {
                recordList.add(new CurrentRecord(record.getId(), record.getPrevious(), record.getValue(), record.getReps()));
            });

            exerciseList.add(new CurrentExercise(exercise.getId(), exercise.getTitle(), exercise.getImage(), recordList));
        });

        return new CurrentWorkout(workoutData.getDate(), exerciseList);
    }
}
