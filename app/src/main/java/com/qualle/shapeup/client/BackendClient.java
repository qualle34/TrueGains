package com.qualle.shapeup.client;

import com.qualle.shapeup.client.api.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BackendClient {

    @GET("/user/1")
    Call<User> getUser();
}
