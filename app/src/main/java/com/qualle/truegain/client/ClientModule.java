package com.qualle.truegain.client;

import com.qualle.truegain.repository.LocalRepository;
import com.qualle.truegain.service.ApiService;
import com.qualle.truegain.service.LocalService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ClientModule {

//    @Provides
//    @Singleton
//    public LocalService provideLocalService() {
//        return new LocalService();
//    }
//
//    @Provides
//    @Singleton
//    public LocalRepository provideLocalRepository() {
//        return new LocalRepository();
//    }

    @Provides
    @Singleton
    public ApiService provideApiService() {
        return new ApiService();
    }

    @Provides
    @Singleton
    public BackendClient provideBackendClient(Retrofit retrofit) {
        return retrofit.create(BackendClient.class);
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
//                .baseUrl("https://truegain-api.fly.dev/") // todo to external properties
                .baseUrl("http://10.0.2.2:8080/") // todo to external properties
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
