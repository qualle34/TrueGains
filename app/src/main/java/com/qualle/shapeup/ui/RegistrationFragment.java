package com.qualle.shapeup.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qualle.shapeup.R;

public class RegistrationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        view.findViewById(R.id.registration_button_next).setOnClickListener(v ->
                navController.navigate(R.id.action_nav_registration_fragment_to_nav_main_fragment));
        return view;
    }
}