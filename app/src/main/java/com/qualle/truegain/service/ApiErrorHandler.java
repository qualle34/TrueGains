package com.qualle.truegain.service;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.ErrorResponse;
import com.qualle.truegain.config.DaggerApplicationComponent;

import java.lang.reflect.Type;

import javax.inject.Inject;

import okhttp3.ResponseBody;

public class ApiErrorHandler implements ErrorHandler {

    private final BackendClient client;

    public ApiErrorHandler(BackendClient client) {
        this.client = client;
    }

    @Override
    public void handle(Context context, ResponseBody body) {
        Gson gson = new Gson();
        Type type = new TypeToken<ErrorResponse>() {}.getType();
        ErrorResponse errorResponse = gson.fromJson(body.charStream(), type);

        // todo
        Toast.makeText(context, errorResponse.getType() + ": " + errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
