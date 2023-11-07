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

    private final RxDataStore<LocalData> workoutDataStore;

    public LocalRepository(Context context) {
        workoutDataStore = new RxDataStoreBuilder<>(context, "localdata.pb", new LocalDataSerializer()).build();
    }

    public synchronized void saveWorkout(WorkoutData currentWorkout) {

        workoutDataStore.updateDataAsync(data -> {

            LocalData newData = data.toBuilder().setWorkout(currentWorkout).build();
            return Single.just(newData);
        });
    }

    public synchronized void saveUser(UserData user) {

        workoutDataStore.updateDataAsync(data -> {

            LocalData newData = data.toBuilder().setUser(user).build();
            return Single.just(newData);
        });
    }

    public synchronized WorkoutData getCurrentWorkout() {
        return workoutDataStore.data().blockingFirst().getWorkout();
    }

    public synchronized UserData getUser() {
        return workoutDataStore.data().blockingFirst().getUser();
    }
}
