package com.qualle.truegain.config;

import com.qualle.truegain.MainActivity;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.model.BottomMenuViewModel;
import com.qualle.truegain.model.CurrentWorkoutViewModel;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.ErrorHandler;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.ui.MainFragment;
import com.qualle.truegain.ui.auth.LoginFragment;
import com.qualle.truegain.ui.menu.BottomMenuFragment;
import com.qualle.truegain.ui.profile.ProfileFragment;
import com.qualle.truegain.ui.workout.SaveWorkoutFragment;
import com.qualle.truegain.ui.workout.WorkoutDetailsFragment;
import com.qualle.truegain.ui.workout.WorkoutListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ClientModule.class)
public interface ApplicationComponent {

    void inject(MainActivity activity);

    void inject(MainFragment fragment);

    void inject(LoginFragment fragment);

    void inject(ErrorHandler handler);

    void inject(AuthenticationHandler service);

    void inject(WorkoutListFragment fragment);

    void inject(BottomMenuFragment fragment);

    void inject(SaveWorkoutFragment fragment);

    void inject(WorkoutDetailsFragment fragment);

    void inject(ProfileFragment fragment);

    void inject(CurrentWorkoutViewModel viewModel);

    void inject(BottomMenuViewModel viewModel);

    BackendClient backendClient();

    LocalService localService();

    ErrorHandler errorHandler();

    AuthenticationHandler authenticationHandler();
}
