package com.qualle.shapeup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.shapeup.adapter.ExerciseAdapter;
import com.qualle.shapeup.client.ShapeUpClient;
import com.qualle.shapeup.databinding.FragmentSaveWorkoutBinding;
import com.qualle.shapeup.model.SaveWorkoutViewModel;
import com.qualle.shapeup.model.dto.Exercise;
import com.qualle.shapeup.model.dto.Record;

import java.util.ArrayList;
import java.util.List;

public class SaveWorkoutFragment extends Fragment {

    private SaveWorkoutViewModel viewModel;

    private FragmentSaveWorkoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSaveWorkoutBinding.inflate(inflater, container, false);

        NavController navController = NavHostFragment.findNavController(this);



        Record record = new Record();
        record.setType("asfd");
        record.setValue(12);
        record.setExercise( ShapeUpClient.getExercises().get(0));

        List<Record> records = new ArrayList<>();
        records.add(record);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ExerciseAdapter adapter = new ExerciseAdapter(records);
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(SaveWorkoutViewModel.class);

        binding.saveWorkoutButtonSave.setOnClickListener(v -> {
//            viewModel.getWorkout().observe();
            navController.navigate(R.id.action_nav_save_workout_fragment_to_nav_save_exercise_fragment);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}