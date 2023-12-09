package com.qualle.truegain.ui.workout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.R;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.Exercise;
import com.qualle.truegain.client.api.Workout;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentSaveWorkoutBinding;
import com.qualle.truegain.model.CurrentWorkoutViewModel;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.ui.adapter.WorkoutExerciseRecyclerViewAdapter;
import com.qualle.truegain.ui.listener.MenuExerciseClickListener;
import com.qualle.truegain.ui.menu.BottomMenuFragment;
import com.qualle.truegain.util.DateFormatterUtil;

import java.time.LocalDateTime;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveWorkoutFragment extends Fragment implements MenuExerciseClickListener {

    private CurrentWorkoutViewModel workoutViewModel;

    @Inject
    public LocalService service;

    @Inject
    public BackendClient client;

    @Inject
    public AuthenticationHandler authenticationHandler;

    private FragmentSaveWorkoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSaveWorkoutBinding.inflate(inflater, container, false);
        workoutViewModel = new ViewModelProvider(this).get(CurrentWorkoutViewModel.class);

        NavController navController = NavHostFragment.findNavController(this);
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        DaggerApplicationComponent.builder()
                .clientModule(ClientModule.getInstance(getContext())).build()
                .inject(this);

        try {
            authenticationHandler.holdAuthentication();

        } catch (Exception e) {
            navController.navigate(R.id.action_nav_save_workout_fragment_to_nav_greeting_fragment);
            return binding.getRoot();
        }

        binding.saveWorkoutButtonBack.setOnClickListener(v -> {
            navController.popBackStack();
            inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });

        client.getWorkoutByUserAndDate(service.getAuthorizationHeader(), DateFormatterUtil.toApiDate(LocalDateTime.now()))
                .enqueue(new Callback<>() {

                    @Override
                    public void onResponse(Call<Workout> call, Response<Workout> response) {

                        if (response.isSuccessful()) {
                            workoutViewModel.setApiWorkout(response.body());

                            RecyclerView recyclerView = binding.saveWorkoutRecyclerView;
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            WorkoutExerciseRecyclerViewAdapter adapter = new WorkoutExerciseRecyclerViewAdapter(getActivity(), workoutViewModel);
                            recyclerView.setAdapter(adapter);

                            workoutViewModel.getWorkout()
                                    .observe(getViewLifecycleOwner(), newName -> adapter.notifyDataSetChanged());

                        }
                    }

                    @Override
                    public void onFailure(Call<Workout> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

        binding.saveWorkoutAddExercise.setOnClickListener(v -> {
            BottomMenuFragment.newInstance(this)
                    .show(getChildFragmentManager(), null);
        });

        return binding.getRoot();
    }

    @Override
    public void onExerciseSelect(long exerciseId) {

        client.getExerciseByIdForUser(service.getAuthorizationHeader(), exerciseId).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                workoutViewModel.createEmptyExercise(response.body());
            }

            @Override
            public void onFailure(Call<Exercise> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        saveWorkout();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        NavController navController = NavHostFragment.findNavController(this);
        WorkoutExerciseRecyclerViewAdapter adapter = null;
        int position = -1;

        try {
            adapter = (WorkoutExerciseRecyclerViewAdapter) binding.saveWorkoutRecyclerView.getAdapter();
            position = adapter.getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }

        switch (item.getItemId()) {
            case 1:
                Bundle args = new Bundle();
                args.putLong("id", adapter.getExerciseIdByPosition());
                navController.navigate(R.id.action_nav_save_workout_fragment_to_nav_exercise_detailed_fragment, args);
                break;

            case 2:
                workoutViewModel.deleteEmptyRecords(position); // todo
                saveWorkout();

                break;
            case 3:
                workoutViewModel.deleteExercise(position);
                saveWorkout();

                break;
        }
        return super.onContextItemSelected(item);
    }

    public void saveWorkout() {

        if (workoutViewModel.getApiWorkout() == null) {
            return;
        }

        client.saveWorkout(service.getAuthorizationHeader(), workoutViewModel.getApiWorkout().getId(), workoutViewModel.getApiWorkout()).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Workout> call, Response<Workout> response) {
                // todo print something
            }

            @Override
            public void onFailure(Call<Workout> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}