package com.qualle.shapeup.service;

import android.content.Context;

import com.qualle.shapeup.model.LocalData;
import com.qualle.shapeup.model.local.CurrentExerciseProto;
import com.qualle.shapeup.model.local.CurrentRecordProto;
import com.qualle.shapeup.model.local.CurrentWorkoutProto;
import com.qualle.shapeup.model.local.ExerciseDetailsProto;
import com.qualle.shapeup.model.local.SimpleWorkoutProto;
import com.qualle.shapeup.model.local.VolumeProto;
import com.qualle.shapeup.model.local.WorkoutDetailsProto;
import com.qualle.shapeup.repository.LocalRepository;
import com.qualle.shapeup.util.DateFormatterUtil;

import java.time.LocalDate;
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
        List<LocalData.WorkoutData.ExerciseData> exerciseDataList = new ArrayList<>();

        currentWorkout.getExercises().forEach(exercise -> {
            List<LocalData.WorkoutData.ExerciseData.RecordData> recordDataList = new ArrayList<>();

            exercise.getRecords().forEach(record -> {
                LocalData.WorkoutData.ExerciseData.RecordData recordData = LocalData.WorkoutData.ExerciseData.RecordData.newBuilder()
                        .setId(record.getId())
                        .setPrevious(record.getPrevious())
                        .setWeight(record.getWeight())
                        .setReps(record.getReps())
                        .build();

                recordDataList.add(recordData);
            });

            exerciseDataList.add(LocalData.WorkoutData.ExerciseData.newBuilder()
                    .setId(exercise.getId())
                    .setName(exercise.getName())
                    .setEquipment(exercise.getEquipment())
                    .setImage(exercise.getImageLink())
                    .addAllRecords(recordDataList)
                    .build());
        });

        LocalData.WorkoutData workoutData = LocalData.WorkoutData.newBuilder()
                .addAllExercises(exerciseDataList)
                .setDate(currentWorkout.getDate().toEpochDay())
                .setId(currentWorkout.getDate().toEpochDay()) // todo
                .build();

        repository.saveWorkout(workoutData);
    }

    public List<SimpleWorkoutProto> getWorkouts() {
        List<LocalData.WorkoutData> workoutData = repository.getWorkouts();

        List<SimpleWorkoutProto> simpleWorkouts = new ArrayList<>();

        for (LocalData.WorkoutData workout : workoutData) {

            simpleWorkouts.add(new SimpleWorkoutProto(
                    workout.getId(),
                    DateFormatterUtil.formatToSimpleDate(workout.getDate()),
                    workout.getExercisesCount(),
                    1));
        }

        return simpleWorkouts;
    }

    public WorkoutDetailsProto getWorkoutById(long id) {
        LocalData.WorkoutData workout = repository.getWorkoutById(id);

        WorkoutDetailsProto workoutDetails = new WorkoutDetailsProto(workout.getId(), LocalDate.ofEpochDay(workout.getDate()), workout.getExercisesCount());

        List<ExerciseDetailsProto> exerciseDetailsList = new ArrayList<>();
        List<VolumeProto> volumeForExercises = new ArrayList<>();
        List<VolumeProto> volumeForBodyParts = new ArrayList<>();
        volumeForExercises.add(new VolumeProto("Overall", 0));

        workout.getExercisesList().forEach(exercise -> {

            ExerciseDetailsProto exerciseDetails = new ExerciseDetailsProto(exercise.getId(), exercise.getName(), exercise.getImage());
            List<ExerciseDetailsProto.RecordDetailsProto> recordDetailsList = new ArrayList<>();

            exercise.getRecordsList().forEach(record ->
                    recordDetailsList.add(new ExerciseDetailsProto.RecordDetailsProto(record.getId(), record.getWeight(), record.getReps())));

            exerciseDetails.setRecords(recordDetailsList);
            exerciseDetailsList.add(exerciseDetails);

            VolumeProto exerciseVolume = new VolumeProto(exercise.getName(),
                    exercise.getRecordsList().stream()
                            .mapToInt(t -> (int) (t.getWeight() * t.getReps()))
                            .sum());

            volumeForExercises.add(exerciseVolume);
            volumeForBodyParts.add(exerciseVolume);

            volumeForExercises.get(0).setValue(volumeForExercises.get(0).getValue() + exerciseVolume.getValue());
        });

        workoutDetails.setExerciseDetailsList(exerciseDetailsList);
        workoutDetails.setVolumeForBodyParts(volumeForBodyParts);
        workoutDetails.setVolumeForExercises(volumeForExercises);

        return workoutDetails;
    }

    public CurrentWorkoutProto getCurrentWorkout() {
        LocalData.WorkoutData workout = repository.getCurrentWorkout();

        if (workout == null) {
            return new CurrentWorkoutProto(LocalDate.now(), new ArrayList<>());
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

        return new CurrentWorkoutProto(LocalDate.ofEpochDay(workout.getDate()), exerciseList);
    }

    public boolean isCurrentWorkoutStarted() {
        return repository.getCurrentWorkout() != null;
    }
}
