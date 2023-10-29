package com.qualle.truegain.client;

import com.qualle.truegain.client.api.Category;
import com.qualle.truegain.client.api.Exercise;
import com.qualle.truegain.client.api.Image;
import com.qualle.truegain.client.api.Record;
import com.qualle.truegain.client.api.RecordSummary;
import com.qualle.truegain.client.api.User;
import com.qualle.truegain.client.api.Workout;
import com.qualle.truegain.model.local.VolumeProto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class InMemoryBackendClient {

    private static List<Workout> workouts;
    private static List<Exercise> exercises;
    private static List<Category> categories;
    private static User user;

    static {
        user = new User("Anton", "01.06.2001", 21, "Male", 174);
        user.setEmail("antonsamoilo@gmail.com");

        exercises = new ArrayList<>();
        exercises.add(new Exercise(1, "Bench press", "Barbell", ""));
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

        categories = new ArrayList<>();
//        categories.add(new Category(1, "Chest", new Image(1, "", "ic_menu_chest")));
//        categories.add(new Category(2, "Back", new Image(2, "", "ic_menu_back")));
//        categories.add(new Category(3, "Biceps", new Image(3, "", "ic_menu_biceps")));
//        categories.add(new Category(4, "Triceps", new Image(4, "", "ic_menu_triceps")));
//        categories.add(new Category(5, "Shoulders", new Image(5, "", "ic_menu_shoulders")));
//        categories.add(new Category(6, "Trapezius", new Image(6, "", "ic_menu_trapeze")));
//        categories.add(new Category(7, "Forearms", new Image(7, "", "ic_menu_forearms")));
//        categories.add(new Category(8, "Abs", new Image(8, "", "ic_menu_abs")));
//        categories.add(new Category(9, "Quads", new Image(9, "", "ic_menu_quads")));
//        categories.add(new Category(10, "Hamstrings", new Image(10, "", "ic_menu_hamstrings")));
//        categories.add(new Category(11, "Glutes", new Image(11, "", "ic_menu_glutes")));
//        categories.add(new Category(12, "Caviar", new Image(12, "", "ic_menu_caviar")));

        workouts = new ArrayList<>();

        List<Record> records = new ArrayList<>();
//        records.add(new Record(exercises.get(0), 80, 10));
//        records.add(new Record(exercises.get(2), 50, 10));
//        records.add(new Record(exercises.get(4), 30, 10));
//        records.add(new Record(exercises.get(6), 30, 10));
//        records.add(new Record(exercises.get(7), 30, 10));
        Workout workout = new Workout();
//        workout.setRecords(records);
        workout.setDate(LocalDateTime.of(2023, 5, 25, 10, 10).toString());

        List<Record> records1 = new ArrayList<>();
//        records1.add(new Record(exercises.get(1), 80, 10));
//        records1.add(new Record(exercises.get(3), 50, 10));
//        records1.add(new Record(exercises.get(5), 30, 10));
//        records1.add(new Record(exercises.get(6), 30, 10));
        Workout workout1 = new Workout();
//        workout1.setRecords(records1);
        workout1.setDate(LocalDateTime.of(2023, 5, 28, 10, 10).toString());

        List<Record> records2 = new ArrayList<>();
//        records2.add(new Record(exercises.get(1), 90, 10));
//        records2.add(new Record(exercises.get(3), 60, 10));
//        records2.add(new Record(exercises.get(5), 40, 10));
        Workout workout2 = new Workout();
//        workout2.setRecords(records2);
        workout2.setDate(LocalDateTime.of(2023, 6, 1, 10, 10).toString());

        List<Record> records3 = new ArrayList<>();
//        records3.add(new Record(exercises.get(0), 90, 10));
//        records3.add(new Record(exercises.get(2), 60, 10));

        Workout workout3 = new Workout();
//        workout3.setRecords(records3);
        workout3.setDate(LocalDateTime.of(2023, 6, 5, 10, 10).toString());

        workouts.add(workout);
        workouts.add(workout1);
        workouts.add(workout2);
        workouts.add(workout3);
    }

    public static List<Exercise> getExercises() {
        return exercises;
    }

    public static Exercise getExercise(long id) {
        for (int i = 1; i < 12; i++) {
            List<Exercise> exercises1 =
                    getExercisesByCategory(i).stream().filter(e -> (id == e.getId()))
                            .collect(Collectors.toList());

            if (!exercises1.isEmpty()) {
                return exercises1.get(0);
            }
        }
        return null;
    }

    public static List<Workout> getWorkouts() {
        return workouts;
    }

    public static List<Category> getCategories() {
        return categories;
    }

    public static List<Exercise> getExercisesByCategory(long category) {
        int cat = (int) category;
        List<Exercise> result = new ArrayList<>();
        switch (cat) {
            case 1:
                result.add(new Exercise(1, "Bench press", "Barbell", ""));
                result.add(new Exercise(2, "Bench press", "Dumbbell", ""));
                break;
            case 2:
                result.add(new Exercise(3, "Lat pulldown", "Machine", ""));
                result.add(new Exercise(4, "Deadlift", "Barbell", ""));
                break;
            case 3:
                result.add(new Exercise(5, "Curls", "Barbell", ""));
                result.add(new Exercise(6, "Curls", "Dumbbell", ""));
                break;
            case 4:
                result.add(new Exercise(7, "French press", "Barbell", ""));
                result.add(new Exercise(8, "Triceps pushdown", "Machine", ""));
                break;
            case 5:
                result.add(new Exercise(9, "Side lateral rise", "Dumbbell", ""));
                break;
            case 6:
                result.add(new Exercise(10, "Shrugs", "Barbell", ""));
                break;
            case 7:
                result.add(new Exercise(11, "Wrist curl", "Barbell", ""));
                result.add(new Exercise(12, "Reverse grip curls", "Barbell", ""));
                break;
            case 8:
                result.add(new Exercise(13, "Crunches", "", ""));
                break;
            case 9:
                result.add(new Exercise(14, "Squat", "Barbell", ""));
                break;
            case 10:
                result.add(new Exercise(15, "Lying leg curls", "Machine", ""));
                break;
            case 11:
                result.add(new Exercise(16, "Romanian deadlift", "Barbell", ""));
                break;
            case 12:
                result.add(new Exercise(17, "Calf Raises", "", ""));
                break;
        }


        return result;
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

    public static User getUser() {
        return user;
    }

    public static List<VolumeProto> getVolumeRecords() {
        List<VolumeProto> records = new ArrayList<>();

//        for (Record record : workouts.get(0).getRecords()) {
//            records.add(new VolumeProto(record.getExercise().getName(), (int) (record.getWeight() * record.getReps())));
//        }

        return records;
    }
}
