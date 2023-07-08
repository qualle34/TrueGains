package com.qualle.shapeup.repository;

import android.content.Context;

import androidx.datastore.rxjava3.RxDataStore;
import androidx.datastore.rxjava3.RxDataStoreBuilder;

import com.qualle.shapeup.model.datastore.WorkoutData;
import com.qualle.shapeup.model.datastore.WorkoutDataSerializer;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class LocalRepository {

    private static volatile LocalRepository instance;

    private final RxDataStore<WorkoutData> dataStore;

    private LocalRepository(Context context) {
        dataStore = new RxDataStoreBuilder<>(context, "workout.pb", new WorkoutDataSerializer()).build();
    }

    public void saveWorkout(WorkoutData workoutData) {
        dataStore.updateDataAsync(currentWorkoutData ->
                Single.just(workoutData));
    }

    public WorkoutData getWorkout() {
        return dataStore.data().blockingFirst();
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
}
