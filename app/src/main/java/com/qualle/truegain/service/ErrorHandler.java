package com.qualle.truegain.service;

import android.content.Context;

import okhttp3.ResponseBody;

public interface ErrorHandler {

    void handle(Context context, ResponseBody body);
}
