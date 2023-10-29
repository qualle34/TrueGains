package com.qualle.truegain.model.datastore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.datastore.core.Serializer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qualle.truegain.model.WorkoutData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

public class ExerciseDataSerializer implements Serializer<WorkoutData.ExerciseData> {

    @Override
    public WorkoutData.ExerciseData getDefaultValue() {
        return WorkoutData.ExerciseData.getDefaultInstance();
    }

    @Nullable
    @Override
    public Object readFrom(@NonNull InputStream inputStream, @NonNull Continuation<? super WorkoutData.ExerciseData> continuation) {
        try {
            return WorkoutData.ExerciseData.parseFrom(inputStream);
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException("Can not read proto", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public Object writeTo(WorkoutData.ExerciseData data, @NonNull OutputStream outputStream, @NonNull Continuation<? super Unit> continuation) {
        try {
            data.writeTo(outputStream);
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
