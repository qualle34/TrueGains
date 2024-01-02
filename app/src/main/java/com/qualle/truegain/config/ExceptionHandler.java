package com.qualle.truegain.config;

import android.util.Log;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    Thread.UncaughtExceptionHandler oldHandler;

    public ExceptionHandler() {

            oldHandler = Thread.getDefaultUncaughtExceptionHandler();

    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.e("UncaughtExceptionHandler", thread.getName() + ": " + throwable.getMessage(), throwable);

        if(oldHandler != null) 
            oldHandler.uncaughtException(thread, throwable);
    }
}
