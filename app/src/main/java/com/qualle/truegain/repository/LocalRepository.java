package com.qualle.truegain.repository;

import android.content.Context;

import androidx.datastore.rxjava3.RxDataStore;
import androidx.datastore.rxjava3.RxDataStoreBuilder;

import com.qualle.truegain.model.LocalData;
import com.qualle.truegain.model.UserData;
import com.qualle.truegain.model.WorkoutData;
import com.qualle.truegain.model.datastore.LocalDataSerializer;

import java.time.LocalDate;

import io.reactivex.rxjava3.core.Single;

public class LocalRepository {

    private static volatile LocalRepository instance;

    private final RxDataStore<LocalData> workoutDataStore;

    private LocalRepository(Context context) {
        workoutDataStore = new RxDataStoreBuilder<>(context, "workoutdata.pb", new LocalDataSerializer()).build();
    }

    public void saveWorkout(WorkoutData currentWorkout) {

        workoutDataStore.updateDataAsync(data -> {

            LocalData newData = data.toBuilder().setWorkout(currentWorkout).build();
            return Single.just(newData);
        });
    }

    public void saveUser(UserData user) {

        workoutDataStore.updateDataAsync(data -> {

            LocalData newData = data.toBuilder().setUser(user).build();
            return Single.just(newData);
        });
    }

    public WorkoutData getCurrentWorkout() {
        return workoutDataStore.data().blockingFirst().getWorkout();
    }

    public UserData getUser() {
        return workoutDataStore.data().blockingFirst().getUser();
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
