package com.qualle.truegain.client;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ClientModule {

    @Provides
    @Singleton
    public BackendClient provideBackendClient(Retrofit retrofit) {
        return retrofit.create(BackendClient.class);
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // todo to external properties
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
