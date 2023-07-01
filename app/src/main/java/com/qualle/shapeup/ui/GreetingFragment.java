package com.qualle.shapeup.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.qualle.shapeup.R;

public class GreetingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_greeting, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        view.findViewById(R.id.greeting_button_login).setOnClickListener(v ->
                navController.navigate(R.id.action_nav_greeting_fragment_to_nav_login_fragment));
        view.findViewById(R.id.greeting_button_registration).setOnClickListener(v ->
                navController.navigate(R.id.action_nav_greeting_fragment_to_nav_registration_fragment));
        return view;
    }
}