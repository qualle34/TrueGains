package com.qualle.truegain.client;

import com.qualle.truegain.client.api.MainPageData;
import com.qualle.truegain.client.api.SimpleWorkout;
import com.qualle.truegain.client.api.User;
import com.qualle.truegain.client.api.Workout;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BackendClient {

    @GET("/user/{id}")
    Call<User> getUser(@Path("id") long id);

    @GET("/workout/simple")
    Call<List<SimpleWorkout>> getWorkoutsByUserId(@Query("userId") long userId);

    @GET("/workout/{id}")
    Call<Workout> getWorkoutById(@Path("id") long id);

    @GET("/main")
    Call<MainPageData> getMainPageData(@Query("userId") long userId);
}
