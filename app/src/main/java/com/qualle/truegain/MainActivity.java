package com.qualle.truegain;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.qualle.truegain.config.ExceptionHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        setContentView(R.layout.activity_main);
    }
}