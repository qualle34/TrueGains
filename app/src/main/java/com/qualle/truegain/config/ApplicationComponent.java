package com.qualle.truegain.config;

import com.qualle.truegain.ui.profile.ProfileFragment;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ClientModule.class)
public interface ApplicationComponent {

    void inject(ProfileFragment fragment);

    BackendClient backendClient();

}
