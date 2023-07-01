package com.qualle.shapeup.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.qualle.shapeup.databinding.FragmentSaveExerciseBinding;
import com.qualle.shapeup.model.SaveWorkoutViewModel;
import com.qualle.shapeup.model.dto.Record;
import com.qualle.shapeup.model.dto.Workout;

import java.util.ArrayList;
import java.util.List;

public class SaveExerciseFragment extends Fragment {

    private static final String[] TYPE_ARRAY = {"На раз", "На количество"};
    private static final String[] EXERCISE_ARRAY = {"Жим лежа", "Становая тяга", "Подьем гантели на бицепс", "Тяга верхнего блока"};

    private SaveWorkoutViewModel viewModel;

    private FragmentSaveExerciseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSaveExerciseBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        ArrayAdapter adapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, TYPE_ARRAY);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.saveExerciseSpinnerType.setAdapter(adapter);

        ArrayAdapter adapter1 = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, EXERCISE_ARRAY);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.saveExerciseSpinnerExercise.setAdapter(adapter1);


        List<Record> records = new ArrayList<>();


        binding.saveExerciseButtonSave.setOnClickListener(v -> {

            Record record = new Record();
            record.setType(binding.saveExerciseSpinnerType.getSelectedItem().toString());
            record.setValue(Integer.parseInt(binding.saveExerciseEditValue.getText().toString()));
//            viewModel.saveWorkout();

            navController.popBackStack();
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SaveWorkoutViewModel.class);

    }
}