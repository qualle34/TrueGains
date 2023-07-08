package com.qualle.shapeup.model.datastore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.datastore.core.Serializer;

import com.google.protobuf.InvalidProtocolBufferException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

public class RecordDataSerializer implements Serializer<WorkoutData.ExerciseData.RecordData> {

    @Override
    public WorkoutData.ExerciseData.RecordData getDefaultValue() {
        return WorkoutData.ExerciseData.RecordData.getDefaultInstance();
    }

    @Nullable
    @Override
    public Object readFrom(@NonNull InputStream inputStream, @NonNull Continuation<? super WorkoutData.ExerciseData.RecordData> continuation) {
        try {
            return WorkoutData.ExerciseData.RecordData.parseFrom(inputStream);
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException("Can not read proto", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public Object writeTo(WorkoutData.ExerciseData.RecordData recordData, @NonNull OutputStream outputStream, @NonNull Continuation<? super Unit> continuation) {
        try {
            recordData.writeTo(outputStream);
            return recordData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
