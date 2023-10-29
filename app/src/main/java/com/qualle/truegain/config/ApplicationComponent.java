package com.qualle.truegain.config;

import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.model.BottomMenuViewModel;
import com.qualle.truegain.model.CurrentWorkoutViewModel;
import com.qualle.truegain.service.ApiService;
import com.qualle.truegain.ui.MainFragment;
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

    void inject(MainFragment fragment);

    void inject(ApiService service);

    void inject(WorkoutListFragment fragment);

    void inject(BottomMenuFragment fragment);

    void inject(SaveWorkoutFragment fragment);

    void inject(WorkoutDetailsFragment fragment);

    void inject(ProfileFragment fragment);

    void inject(CurrentWorkoutViewModel viewModel);

    void inject(BottomMenuViewModel viewModel);

    BackendClient backendClient();

}
