package com.qualle.truegain.client;

import com.qualle.truegain.client.api.Category;
import com.qualle.truegain.client.api.Exercise;
import com.qualle.truegain.client.api.MainPageData;
import com.qualle.truegain.client.api.SimpleWorkout;
import com.qualle.truegain.client.api.User;
import com.qualle.truegain.client.api.Workout;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BackendClient {

    @GET("/user/{id}")
    Call<User> getUser(@Path("id") long id);

    @GET("/workout/simple")
    Call<List<SimpleWorkout>> getWorkoutsByUserId(@Query("userId") long userId);

    @GET("/workout/{id}")
    Call<Workout> getWorkoutById(@Path("id") long id);

    @GET("/workout") // Create workout if not found
    Call<Workout> getWorkoutByUserIdAndDate(@Query("userId") long userId, @Query("date") String date);

    @POST("/workout")
    Call<Workout> saveWorkout(@Body Workout workout);

    @GET("/main")
    Call<MainPageData> getMainPageData(@Query("userId") long userId);

    @GET("/category")
    Call<List<Category>> getCategories(@Query("fetch") String fetch);

    @GET("/exercise")
    Call<List<Exercise>> getExercises(@Query("category-id") long categoryId);

    @GET("/exercise/{id}")
    Call<Exercise> getExerciseByIdForUserId(@Path("id") long id, @Query("user-id") long userId);
}
