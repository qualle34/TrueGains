package com.qualle.truegain.ui.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.api.Workout;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentSaveWorkoutBinding;
import com.qualle.truegain.model.CurrentWorkoutViewModel;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.ui.adapter.WorkoutExerciseRecyclerViewAdapter;
import com.qualle.truegain.ui.menu.BottomMenuFragment;
import com.qualle.truegain.util.DateFormatterUtil;

import java.time.LocalDateTime;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveWorkoutFragment extends Fragment {

    private CurrentWorkoutViewModel workoutViewModel;
    private LocalService localService;

    @Inject
    public BackendClient client;

    private FragmentSaveWorkoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSaveWorkoutBinding.inflate(inflater, container, false);
        localService = LocalService.getInstance(getContext());
        workoutViewModel = new ViewModelProvider(this).get(CurrentWorkoutViewModel.class);

        NavController navController = NavHostFragment.findNavController(this);
        DaggerApplicationComponent.create().inject(this);

        binding.saveWorkoutButtonBack.setOnClickListener(v -> navController.popBackStack());

        client.getWorkoutByUserIdAndDate(1, DateFormatterUtil.toApiDate(LocalDateTime.now())).enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<Workout> call, Response<Workout> response) {

                workoutViewModel.setApiWorkout(response.body());

                RecyclerView recyclerView = binding.saveWorkoutRecyclerView;
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                WorkoutExerciseRecyclerViewAdapter adapter = new WorkoutExerciseRecyclerViewAdapter(getActivity(), workoutViewModel);
                recyclerView.setAdapter(adapter);

                workoutViewModel.getWorkout()
                        .observe(getViewLifecycleOwner(), newName -> adapter.notifyDataSetChanged());

            }

            @Override
            public void onFailure(Call<Workout> call, Throwable t) {
                t.printStackTrace();
            }
        });

        binding.saveWorkoutAddExercise.setOnClickListener(v -> {
            new BottomMenuFragment().show(getChildFragmentManager(), null);
        });

        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();

        client.saveWorkout(workoutViewModel.getApiWorkout()).enqueue(new Callback<Workout>() {
            @Override
            public void onResponse(Call<Workout> call, Response<Workout> response) {
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<Workout> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}