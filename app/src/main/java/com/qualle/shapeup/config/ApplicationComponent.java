package com.qualle.shapeup.config;

import com.qualle.shapeup.ui.profile.ProfileFragment;
import com.qualle.shapeup.client.BackendClient;
import com.qualle.shapeup.client.ClientModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ClientModule.class)
public interface ApplicationComponent {

    void inject(ProfileFragment fragment);

    BackendClient backendClient();

}
