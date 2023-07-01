package com.qualle.shapeup.client;

import com.qualle.shapeup.model.dto.Exercise;
import com.qualle.shapeup.model.dto.Record;
import com.qualle.shapeup.model.dto.RecordSummary;
import com.qualle.shapeup.model.dto.Workout;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InMemoryBackendClient {

    private static List<Workout> workouts;
    private static List<Exercise> exercises;

    static  {
        exercises = new ArrayList<>();
        exercises.add(new Exercise("Barbell bench press", ""));
        exercises.add(new Exercise("Dumbbell bench press", ""));
        exercises.add(new Exercise("Lat pulldown", ""));
        exercises.add(new Exercise("Barbell deadlift", ""));
        exercises.add(new Exercise("Barbell curls", ""));
        exercises.add(new Exercise("Dumbbell curls", ""));
        exercises.add(new Exercise("Barbell french press", ""));
        exercises.add(new Exercise("Triceps pushdown", ""));
        exercises.add(new Exercise("Barbell wrist curl", ""));
        exercises.add(new Exercise("Reverse grip barbell curls", ""));
        exercises.add(new Exercise("Dumbbell side lateral rise", ""));
        exercises.add(new Exercise("Barbell squat", ""));
        exercises.add(new Exercise("Lying leg curls", ""));
        exercises.add(new Exercise("Calf Raises", ""));
        exercises.add(new Exercise("Crunches", ""));

        workouts = new ArrayList<>();

        List<Record> records = new ArrayList<>();
        records.add(new Record(exercises.get(0), 80, "weight"));
        records.add(new Record(exercises.get(2), 50, "weight"));
        records.add(new Record(exercises.get(4), 30, "weight"));
        records.add(new Record(exercises.get(6), 30, "weight"));
        records.add(new Record(exercises.get(7), 30, "weight"));
        Workout workout = new Workout();
        workout.setAchievementsCount(1);
        workout.setRecords(records);
        workout.setDate(LocalDateTime.of(2023, 5, 25, 10, 10));

        List<Record> records1 = new ArrayList<>();
        records1.add(new Record(exercises.get(1), 80, "weight"));
        records1.add(new Record(exercises.get(3), 50, "weight"));
        records1.add(new Record(exercises.get(5), 30, "weight"));
        records1.add(new Record(exercises.get(6), 30, "weight"));
        Workout workout1 = new Workout();
        workout1.setAchievementsCount(2);
        workout1.setRecords(records1);
        workout1.setDate(LocalDateTime.of(2023, 5, 28, 10, 10));

        List<Record> records2 = new ArrayList<>();
        records2.add(new Record(exercises.get(1), 90, "weight"));
        records2.add(new Record(exercises.get(3), 60, "weight"));
        records2.add(new Record(exercises.get(5), 40, "weight"));
        Workout workout2 = new Workout();
        workout2.setAchievementsCount(3);
        workout2.setRecords(records2);
        workout2.setDate(LocalDateTime.of(2023, 6, 1, 10, 10));

        List<Record> records3 = new ArrayList<>();
        records3.add(new Record(exercises.get(0), 90, "weight"));
        records3.add(new Record(exercises.get(2), 60, "weight"));

        Workout workout3 = new Workout();
        workout3.setAchievementsCount(0);
        workout3.setRecords(records3);
        workout3.setDate(LocalDateTime.of(2023, 6, 5, 10, 10));

        workouts.add(workout);
        workouts.add(workout1);
        workouts.add(workout2);
        workouts.add(workout3);
    }

    public static List<Exercise> getExercises() {
        return exercises;
    }

    public static List<Workout> getWorkouts() {
        return workouts;
    }

    public static List<RecordSummary> getRecordsGroupByExercise() {
        List<RecordSummary> recordSummaries = new ArrayList<>();

        recordSummaries.add(new RecordSummary(exercises.get(0), 12));
        recordSummaries.add(new RecordSummary(exercises.get(2), 3));
        recordSummaries.add(new RecordSummary(exercises.get(4), 8));
        recordSummaries.add(new RecordSummary(exercises.get(6), 5));

        return recordSummaries;
    }

    public static void saveWorkout(Workout workout) {
        workouts.add(workout);
    }

    public static Map<Float, Float> getChart() {
        Map<Float, Float> data = new TreeMap<>();
        data.put(20f, 2f);
        data.put(21f, 5f);
        data.put(22f, 1f);
        data.put(23f, 3f);
        return data;
    }
}
