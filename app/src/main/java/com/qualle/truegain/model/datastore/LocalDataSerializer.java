package com.qualle.truegain.model.datastore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.datastore.core.Serializer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qualle.truegain.model.LocalData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

public class LocalDataSerializer implements Serializer<LocalData> {

    @Override
    public LocalData getDefaultValue() {
        return LocalData.getDefaultInstance();
    }

    @Nullable
    @Override
    public Object readFrom(@NonNull InputStream inputStream, @NonNull Continuation<? super LocalData> continuation) {
        try {
            return LocalData.parseFrom(inputStream);
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException("Can not read proto", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public Object writeTo(LocalData recordData, @NonNull OutputStream outputStream, @NonNull Continuation<? super Unit> continuation) {
        try {
            recordData.writeTo(outputStream);
            return recordData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
