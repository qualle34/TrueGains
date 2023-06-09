package com.qualle.shapeup.client;

import com.qualle.shapeup.model.dto.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ShapeUpClient {

    public static List<Exercise> getExercises() {
        List<Exercise> exercises = new ArrayList<>();

        exercises.add(new Exercise("Bench press", ""));
        exercises.add(new Exercise("Lat pulldown", ""));

        return exercises;
    }
}
