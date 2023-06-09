package com.qualle.shapeup;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.qualle.shapeup.databinding.FragmentLoginBinding;
import com.qualle.shapeup.databinding.FragmentMainBinding;
import com.qualle.shapeup.model.enums.ChartType;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        binding.mainButtonProfile.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_main_fragment_to_nav_profile_fragment));

        binding.mainButtonChartMenu.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_main_fragment_to_nav_chart_menu_fragment));

        binding.mainButtonSaveWorkout.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_main_fragment_to_nav_save_workout_fragment));


        Map<Float, Float> data = new TreeMap<>();
        data.put(20f, 2f);
        data.put(21f, 5f);
        data.put(22f, 1f);
        data.put(23f, 3f);

        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.main_chart_container, ChartFragment.newInstance("Workouts per week", ChartType.DATE, data), null)
                .commit();

        return binding.getRoot();
    }
}