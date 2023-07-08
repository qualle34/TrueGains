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

public class WorkoutDataSerializer implements Serializer<WorkoutData> {

    @Override
    public WorkoutData getDefaultValue() {
        return WorkoutData.getDefaultInstance();
    }

    @Nullable
    @Override
    public Object readFrom(@NonNull InputStream inputStream, @NonNull Continuation<? super WorkoutData> continuation) {
        try {
            return WorkoutData.parseFrom(inputStream);
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException("Can not read proto", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public Object writeTo(WorkoutData recordData, @NonNull OutputStream outputStream, @NonNull Continuation<? super Unit> continuation) {
        try {
            recordData.writeTo(outputStream);
            return recordData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
