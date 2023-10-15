package com.qualle.truegain.client;

import com.qualle.truegain.client.api.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BackendClient {

    @GET("/user/1")
    Call<User> getUser();

}
