package com.qualle.truegain.config;

import com.qualle.truegain.MainActivity;
import com.qualle.truegain.ui.profile.MeasureDetailsFragment;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.model.BottomMenuViewModel;
import com.qualle.truegain.model.CurrentWorkoutViewModel;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.ErrorHandler;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.ui.MainFragment;
import com.qualle.truegain.ui.auth.LoginFragment;
import com.qualle.truegain.ui.auth.RegistrationFragment;
import com.qualle.truegain.ui.auth.VerifyRegistrationFragment;
import com.qualle.truegain.ui.menu.BottomMenuFragment;
import com.qualle.truegain.ui.menu.ExerciseDetailedFragment;
import com.qualle.truegain.ui.profile.MeasureListFragment;
import com.qualle.truegain.ui.profile.ProfileEditFragment;
import com.qualle.truegain.ui.profile.ProfileFragment;
import com.qualle.truegain.ui.profile.ProfileSecurityEmailFragment;
import com.qualle.truegain.ui.profile.ProfileSecurityFragment;
import com.qualle.truegain.ui.profile.ProfileSecurityPasswordFragment;
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

    void inject(RegistrationFragment fragment);

    void inject(VerifyRegistrationFragment fragment);

    void inject(ErrorHandler handler);

    void inject(AuthenticationHandler service);

    void inject(WorkoutListFragment fragment);

    void inject(ExerciseDetailedFragment fragment);

    void inject(BottomMenuFragment fragment);

    void inject(SaveWorkoutFragment fragment);

    void inject(WorkoutDetailsFragment fragment);

    void inject(ProfileFragment fragment);

    void inject(ProfileSecurityEmailFragment fragment);

    void inject(ProfileSecurityPasswordFragment fragment);

    void inject(ProfileEditFragment fragment);

    void inject(ProfileSecurityFragment fragment);

    void inject(CurrentWorkoutViewModel viewModel);

    void inject(BottomMenuViewModel viewModel);

    void inject(MeasureListFragment measureListFragment);

    void inject(MeasureDetailsFragment measureDetailsFragment);

    BackendClient backendClient();

    LocalService localService();

    ErrorHandler errorHandler();

    AuthenticationHandler authenticationHandler();
}
