package com.qualle.truegain.service;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qualle.truegain.client.api.ErrorResponse;
import com.qualle.truegain.model.exception.ApiAuthenticationException;

import okhttp3.ResponseBody;

public class ApiErrorHandler implements ErrorHandler {

    private final Gson gson;

    public ApiErrorHandler() {
        this.gson = new Gson();
    }

    @Override
    public void handle(Context context, ResponseBody body) {
        ErrorResponse errorResponse;
        try {
            errorResponse = gson.fromJson(body.charStream(), ErrorResponse.class);
        } catch (Exception e) {
            return;
        }

        switch (errorResponse.getType()) {

            case ACCESS_DENIED:
                throw new ApiAuthenticationException("Authentication exception: " + errorResponse.getMessage());

            case VALIDATION_FAIL:

                String reason = errorResponse.getAdditional().get("reason");
                String message = "Check your data, something is wrong";

                if ("login_found".equals(reason)) {
                    message = "A user with this login already exists";
                } else if ("email_found".equals(reason)) {
                    message = "A user with this email already exists";
                } else if ("week_password".equals(reason)) {
                    message = "Your password is weak";
                }

                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(context, "Server error: " + errorResponse.getType(), Toast.LENGTH_SHORT).show();

        }


    }
}
