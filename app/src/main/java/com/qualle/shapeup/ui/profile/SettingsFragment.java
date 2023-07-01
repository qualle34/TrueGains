package com.qualle.shapeup.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qualle.shapeup.R;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_settings, container, false);
            NavController navController = NavHostFragment.findNavController(this);

            view.findViewById(R.id.settings_button).setOnClickListener(v ->
                    navController.popBackStack());

        view.findViewById(R.id.settings_button_logout).setOnClickListener(v ->
                navController.navigate(R.id.action_nav_settings_fragment_to_nav_greeting_fragment));
            return view;
    }

}