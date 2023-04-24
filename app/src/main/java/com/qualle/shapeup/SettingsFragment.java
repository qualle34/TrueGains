package com.qualle.shapeup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_settings, container, false);
            NavController navController = NavHostFragment.findNavController(this);

            view.findViewById(R.id.settings_button).setOnClickListener(v ->
                    navController.popBackStack());

        view.findViewById(R.id.settings_button_logout).setOnClickListener(v ->
                navController.navigate(R.id.action_settings_fragment_to_greetingFragment));
            return view;
    }

}