package com.qualle.shapeup.model.datastore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.datastore.core.Serializer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qualle.shapeup.model.CategoryData;
import com.qualle.shapeup.model.LocalData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

public class CategoryDataSerializer implements Serializer<CategoryData> {

    @Override
    public CategoryData getDefaultValue() {
        return CategoryData.getDefaultInstance();
    }

    @Nullable
    @Override
    public Object readFrom(@NonNull InputStream inputStream, @NonNull Continuation<? super CategoryData> continuation) {
        try {
            return CategoryData.parseFrom(inputStream);
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException("Can not read proto", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public Object writeTo(CategoryData data, @NonNull OutputStream outputStream, @NonNull Continuation<? super Unit> continuation) {
        try {
            data.writeTo(outputStream);
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
