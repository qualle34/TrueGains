package com.qualle.shapeup.repository;

import android.content.Context;

import androidx.datastore.rxjava3.RxDataStore;
import androidx.datastore.rxjava3.RxDataStoreBuilder;

import com.qualle.shapeup.model.CategoryData;
import com.qualle.shapeup.model.LocalData;
import com.qualle.shapeup.model.datastore.CategoryDataSerializer;
import com.qualle.shapeup.model.datastore.LocalDataSerializer;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;

public class LocalRepository {

    private static volatile LocalRepository instance;

    private final RxDataStore<LocalData> workoutDataStore;
    private final RxDataStore<CategoryData> categoryDataStore;

    private LocalRepository(Context context) {
        workoutDataStore = new RxDataStoreBuilder<>(context, "workoutdata.pb", new LocalDataSerializer()).build();
        categoryDataStore = new RxDataStoreBuilder<>(context, "categorydata.pb", new CategoryDataSerializer()).build();
    }

    public void saveWorkout(LocalData.WorkoutData currentWorkout) {

        workoutDataStore.updateDataAsync(data -> {

            if (data.getWorkoutCount() == 0) {

                LocalData newData = data.toBuilder().addWorkout(currentWorkout).build();
                return Single.just(newData);
            }


            for (int i = 0; i < data.getWorkoutCount(); i++) {

                if (isCurrentWorkout(data.getWorkout(i))) {
                    LocalData newData = data.toBuilder().setWorkout(i, currentWorkout).build();
                    return Single.just(newData);
                }
            }

            LocalData newData = data.toBuilder().addWorkout(currentWorkout).build();
            return Single.just(newData);

        });
    }

    public Map<Integer, Integer> getWorkoutsFromLastWeek(int weekCount) {
       int currentWeek = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());

        Map<Integer, Integer> workoutsPerWeek = new HashMap<>();

        for (int i = 0; i < weekCount; i++) {
            workoutsPerWeek.put(currentWeek - i, 0);
        }

        for (LocalData.WorkoutData workout : getWorkoutData().getWorkoutList()) {

            int workoutWeek = LocalDate.ofEpochDay(workout.getDate()).get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());

            if (workoutWeek >= currentWeek - weekCount) {

                if (workoutsPerWeek.containsKey(workoutWeek)) {
                    workoutsPerWeek.put(workoutWeek, workoutsPerWeek.get(workoutWeek) + 1);
                } else {
                    workoutsPerWeek.put(workoutWeek, 1);
                }
            }
        }

        return workoutsPerWeek;
    }

    public List<LocalData.WorkoutData> getWorkouts() {
        return getWorkoutData().getWorkoutList();
    }

    public LocalData.WorkoutData getWorkoutById(long id) {

        LocalData data = getWorkoutData();
        for (LocalData.WorkoutData workout : data.getWorkoutList()) {

            if (workout.getId() == id) {
                return workout;
            }
        }
        return null;
    }

    public LocalData.WorkoutData getCurrentWorkout() {
        LocalData data = getWorkoutData();
        for (LocalData.WorkoutData workout : data.getWorkoutList()) {

            if (isCurrentWorkout(workout)) {
                return workout;
            }
        }
        return null;
    }

    public CategoryData getCategories() {
        return categoryDataStore.data().blockingFirst();
    }


    private LocalData getWorkoutData() {
        return workoutDataStore.data().blockingFirst();
    }

    public static LocalRepository getInstance(Context context) {
        LocalRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (LocalRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new LocalRepository(context);
                }
            }
        }
        return localInstance;
    }

    private static boolean isCurrentWorkout(LocalData.WorkoutData workout) {
        return LocalDate.now().toEpochDay() == workout.getDate();
    }
}
