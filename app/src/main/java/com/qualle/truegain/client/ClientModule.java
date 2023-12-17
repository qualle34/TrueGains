package com.qualle.truegain.client;

import android.content.Context;

import com.qualle.truegain.repository.LocalRepository;
import com.qualle.truegain.service.ApiAuthenticationHandler;
import com.qualle.truegain.service.ApiErrorHandler;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.ErrorHandler;
import com.qualle.truegain.service.LocalService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ClientModule {

    private static volatile ClientModule instance;

    private Context context;

    private ClientModule (Context context) {
        this.context = context;
    }

    public static ClientModule getInstance(Context context) {
        ClientModule localInstance = instance;
        if (localInstance == null) {
            synchronized (ClientModule.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ClientModule(context);
                }
            }
        }
        return localInstance;
    }

    @Provides
    @Singleton
    public LocalService provideLocalService() {
        return LocalService.getInstance(context);
    }

    @Provides
    @Singleton
    public ErrorHandler provideErrorHandler() {
        return new ApiErrorHandler();
    }

    @Provides
    @Singleton
    public AuthenticationHandler provideAuthenticationHandler(BackendClient client, LocalService service) {
        return new ApiAuthenticationHandler(client, service);
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
                .baseUrl("https://truegain-api.fly.dev/") // todo to external properties
//                .baseUrl("http://10.0.2.2:8080/") // todo to external properties
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
