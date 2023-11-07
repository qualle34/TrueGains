package com.qualle.truegain.client;

import com.qualle.truegain.client.api.Category;
import com.qualle.truegain.client.api.Exercise;
import com.qualle.truegain.client.api.LoginPasswordAuthentication;
import com.qualle.truegain.client.api.MainPageData;
import com.qualle.truegain.client.api.SimpleWorkout;
import com.qualle.truegain.client.api.Token;
import com.qualle.truegain.client.api.TokenAuthentication;
import com.qualle.truegain.client.api.User;
import com.qualle.truegain.client.api.Workout;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BackendClient {

    @POST("/login")
    Call<Token> login(@Body LoginPasswordAuthentication authentication);

    @POST("/refresh")
    Call<Token> refresh(@Body TokenAuthentication authentication);

    @POST("/logout")
    Call<Void> logout(@Body TokenAuthentication authentication);

    @GET("/private/user")
    Call<User> getUser(@Header("Authorization") String auth);

    @GET("/private/profile")
    Call<User> getProfile(@Header("Authorization") String auth);

    @GET("/private/workout/simple")
    Call<List<SimpleWorkout>> getWorkoutsByUser(@Header("Authorization") String auth);

    @GET("/private/workout/{id}")
    Call<Workout> getWorkoutById(@Header("Authorization") String auth, @Path("id") long id);

    @GET("/private/workout")
        // Create workout if not found
    Call<Workout> getWorkoutByUserAndDate(@Header("Authorization") String auth, @Query("date") String date);

    @POST("/private/workout")
    Call<Workout> saveWorkout(@Header("Authorization") String auth, @Body Workout workout);

    @GET("/private/main")
    Call<MainPageData> getMainPageData(@Header("Authorization") String auth);

    @GET("/category")
    Call<List<Category>> getCategories(@Query("fetch") String fetch);

    @GET("/exercise")
    Call<List<Exercise>> getExercises(@Query("category-id") long categoryId);

    @GET("/private/exercise/{id}")
    Call<Exercise> getExerciseByIdForUser(@Header("Authorization") String auth, @Path("id") long id);
}
