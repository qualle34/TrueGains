package com.qualle.shapeup.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.shapeup.model.local.CurrentWorkoutProto;
import com.qualle.shapeup.databinding.FragmentSaveWorkoutBinding;
import com.qualle.shapeup.model.CurrentWorkoutViewModel;
import com.qualle.shapeup.service.LocalService;
import com.qualle.shapeup.ui.adapter.WorkoutExerciseRecyclerViewAdapter;
import com.qualle.shapeup.ui.menu.BottomMenuFragment;

public class SaveWorkoutFragment extends Fragment {

    private CurrentWorkoutViewModel workoutViewModel;
    private LocalService service;

    private FragmentSaveWorkoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSaveWorkoutBinding.inflate(inflater, container, false);
        service = LocalService.getInstance(getContext());
        NavController navController = NavHostFragment.findNavController(this);

        binding.saveWorkoutButtonBack.setOnClickListener(v -> navController.popBackStack());

        workoutViewModel = new ViewModelProvider(this).get(CurrentWorkoutViewModel.class);
        CurrentWorkoutProto workout = service.getCurrentWorkout();

        if ("".equals(workout.getDate())) {
            workoutViewModel.initialize();
        } else {
            workoutViewModel.setData(workout);
        }


        RecyclerView recyclerView = binding.saveWorkoutRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        WorkoutExerciseRecyclerViewAdapter adapter = new WorkoutExerciseRecyclerViewAdapter(workoutViewModel);
        recyclerView.setAdapter(adapter);

        workoutViewModel.getWorkout()
                .observe(getViewLifecycleOwner(), newName -> adapter.notifyDataSetChanged());

        binding.saveWorkoutAddExercise.setOnClickListener(v -> {
            new BottomMenuFragment().show(getChildFragmentManager(), null);
        });

        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        service.saveWorkout(workoutViewModel.getWorkout().getValue());
    }

}