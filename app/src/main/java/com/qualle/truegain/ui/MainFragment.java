package com.qualle.truegain.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.R;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.MainPageData;
import com.qualle.truegain.client.api.SimpleExercise;
import com.qualle.truegain.client.api.SimpleWorkout;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentMainBinding;
import com.qualle.truegain.model.exception.ApiAuthenticationException;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.ErrorHandler;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.ui.adapter.MainExerciseListRecyclerViewAdapter;
import com.qualle.truegain.ui.adapter.MainWorkoutListRecyclerViewAdapter;
import com.qualle.truegain.ui.chart.ChartBarFragment;
import com.qualle.truegain.ui.chart.ChartRadarFragment;
import com.qualle.truegain.ui.listener.ExerciseListClickListener;
import com.qualle.truegain.ui.listener.MenuExerciseClickListener;
import com.qualle.truegain.ui.listener.WorkoutListClickListener;
import com.qualle.truegain.ui.menu.BottomMenuFragment;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment implements WorkoutListClickListener, ExerciseListClickListener, MenuExerciseClickListener {

    private FragmentMainBinding binding;

    @Inject
    public LocalService service;

    @Inject
    public BackendClient client;

    @Inject
    public AuthenticationHandler authenticationHandler;

    @Inject
    public ErrorHandler errorHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        Context context = binding.getRoot().getContext();
        MainFragment fragment = this;

        DaggerApplicationComponent.builder()
                .clientModule(ClientModule.getInstance(getContext())).build()
                .inject(this);

        NavController navController = NavHostFragment.findNavController(this);

        binding.mainProgressBar.setVisibility(View.VISIBLE);

        try {
            authenticationHandler.holdAuthentication();
        } catch (Exception e) {
            navController.navigate(R.id.action_nav_main_fragment_to_nav_greeting_fragment);
            return binding.getRoot();
        }

        binding.mainButtonProfile.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_main_fragment_to_nav_profile_fragment));

        binding.mainButtonAllWorkouts.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_main_fragment_to_workoutListFragment));

        binding.mainButtonStartWorkout.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_main_fragment_to_nav_save_workout_fragment));

        if (service.isCurrentWorkoutStarted()) {
            binding.mainButtonStartWorkout.setText("Continue\nworkout");
        }

        binding.mainButtonAllExercises.setOnClickListener(v -> {
            BottomMenuFragment.newInstance(this).show(getChildFragmentManager(), null);
        });

        RecyclerView recyclerView = binding.mainWorkoutListRecyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView exercisesRecyclerView = binding.mainExercisesListRecyclerView;
        LinearLayoutManager exercisesLayoutManager = new LinearLayoutManager(context);
        exercisesLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        exercisesRecyclerView.setLayoutManager(exercisesLayoutManager);

        client.getMainPageData(service.getAuthorizationHeader()).enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<MainPageData> call, Response<MainPageData> response) {

                if (response.isSuccessful()) {
                    MainPageData dto = response.body();
                    List<SimpleWorkout> workouts = dto.getRecentWorkouts();
                    List<SimpleExercise> exercises = dto.getFrequentExercises();

                    recyclerView.setAdapter(new MainWorkoutListRecyclerViewAdapter(fragment, workouts));

                    exercisesRecyclerView.setAdapter(new MainExerciseListRecyclerViewAdapter(fragment, exercises));

                    if (isAdded() && !getActivity().isChangingConfigurations()) {

                        try {
                            getChildFragmentManager().beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.main_chart_container, ChartBarFragment.newInstance(dto.getWorkoutPerWeekChartData()), null)
                                    .commit();

                        } catch (Exception ignored) {

                        }

                        try {
                            getChildFragmentManager().beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.main_radar_chart_container, ChartRadarFragment.newInstance(dto.getMuscleDistributionChartData()), null)
                                    .commit();

                        } catch (Exception ignored) {

                        }
                    }

                } else {

                    try {
                        errorHandler.handle(getContext(), response.errorBody());
                    } catch (ApiAuthenticationException e) {
                        navController.navigate(R.id.action_nav_main_fragment_to_nav_greeting_fragment);
                    }

                }
            }

            @Override
            public void onFailure(Call<MainPageData> call, Throwable t) {
                navController.navigate(R.id.action_nav_main_fragment_to_nav_greeting_fragment);
            }
        });

        binding.mainProgressBar.setVisibility(View.GONE);

        return binding.getRoot();
    }

    @Override
    public void onWorkoutClick(long workoutId) {
        NavController navController = NavHostFragment.findNavController(this);

        Bundle args = new Bundle();
        args.putLong("id", workoutId);

        navController.navigate(R.id.action_nav_main_fragment_to_nav_workout_details_fragment, args);
    }

    @Override
    public void onExerciseClick(long exerciseId) {
        navigateToSelectedExercise(exerciseId);
    }

    @Override
    public void onExerciseSelect(long exerciseId) {
        navigateToSelectedExercise(exerciseId);
    }

    private void navigateToSelectedExercise(long exerciseId) {
        NavController navController = NavHostFragment.findNavController(this);

        Bundle args = new Bundle();
        args.putLong("id", exerciseId);

        navController.navigate(R.id.action_nav_main_fragment_to_nav_exercise_detailed_fragment, args);
    }
}