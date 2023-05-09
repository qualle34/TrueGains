package com.qualle.shapeup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class SaveWorkoutResultFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save_workout_result, container, false);
        NavController navController = NavHostFragment.findNavController(this);


        String[] types = { "На раз", "На количество"};
        String[] types1 = { "Жим лежа", "Становая тяга", "Подьем гантели на бицепс", "Тяга верхнего блока"};

        Spinner spinner = view.findViewById(R.id.save_workout_spinner_type);
        Spinner spinner1 = view.findViewById(R.id.save_workout_spinner_exercise);

        ArrayAdapter adapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter adapter1 = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, types1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        view.findViewById(R.id.save_workout_button_save).setOnClickListener(v ->
                navController.popBackStack());


        return view;
    }
}