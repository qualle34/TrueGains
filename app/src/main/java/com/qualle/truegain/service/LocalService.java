package com.qualle.truegain.service;

import android.content.Context;

import com.qualle.truegain.model.WorkoutData;
import com.qualle.truegain.model.local.CurrentExerciseProto;
import com.qualle.truegain.model.local.CurrentRecordProto;
import com.qualle.truegain.model.local.CurrentWorkoutProto;
import com.qualle.truegain.repository.LocalRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    public void saveWorkout(CurrentWorkoutProto currentWorkout) {
        List<WorkoutData.ExerciseData> exerciseDataList = new ArrayList<>();

        currentWorkout.getExercises().forEach(exercise -> {
            List<WorkoutData.ExerciseData.RecordData> recordDataList = new ArrayList<>();

            exercise.getRecords().forEach(record -> {
                WorkoutData.ExerciseData.RecordData recordData = WorkoutData.ExerciseData.RecordData.newBuilder()
                        .setId(record.getId())
                        .setPrevious(record.getPrevious())
                        .setWeight(record.getWeight())
                        .setReps(record.getReps())
                        .build();

                recordDataList.add(recordData);
            });

            exerciseDataList.add(WorkoutData.ExerciseData.newBuilder()
                    .setId(exercise.getId())
                    .setName(exercise.getName())
                    .setEquipment(exercise.getEquipment())
                    .setImage(exercise.getImageLink())
                    .addAllRecords(recordDataList)
                    .build());
        });

        WorkoutData workoutData = WorkoutData.newBuilder()
                .addAllExercises(exerciseDataList)
                .setDate(currentWorkout.getDate().toEpochSecond(ZoneOffset.UTC)) // todo
                .setId(currentWorkout.getId())
                .build();

        repository.saveWorkout(workoutData);
    }

    public CurrentWorkoutProto getCurrentWorkout() {
        WorkoutData workout = repository.getCurrentWorkout();

        if (workout == null) {
            return new CurrentWorkoutProto(LocalDateTime.now(), new ArrayList<>());
        }

        List<CurrentExerciseProto> exerciseList = new ArrayList<>();

        if (workout.getExercisesList() != null) {

            workout.getExercisesList().forEach(exercise -> {
                List<CurrentRecordProto> recordList = new ArrayList<>();

                exercise.getRecordsList().forEach(record -> {
                    recordList.add(new CurrentRecordProto(record.getId(), record.getPrevious(), record.getWeight(), record.getReps()));
                });

                exerciseList.add(new CurrentExerciseProto(exercise.getId(), exercise.getName(), exercise.getEquipment(), exercise.getImage(), recordList));
            });
        }

        return new CurrentWorkoutProto(LocalDateTime.ofEpochSecond(workout.getDate(), 0, ZoneOffset.UTC), exerciseList);
    }

    public boolean isCurrentWorkoutStarted() {
        return repository.getCurrentWorkout() != null;
    }
}
