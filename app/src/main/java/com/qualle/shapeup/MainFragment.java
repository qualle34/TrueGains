package com.qualle.shapeup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.qualle.shapeup.client.InMemoryBackendClient;
import com.qualle.shapeup.databinding.FragmentMainBinding;
import com.qualle.shapeup.model.dto.Workout;

import java.util.List;
import java.util.Map;

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


        Map<Float, Float> testChartData = InMemoryBackendClient.getChart();

        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.main_chart_container, BarChartFragment.newInstance(testChartData), null)
                .commit();


        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        LinearLayout linearLayout = binding.mainLinearLayoutWorkout;

        List<Workout> workouts = InMemoryBackendClient.getWorkouts();

        FrameLayout addWorkoutCard = new FrameLayout(binding.getRoot().getContext());
        addWorkoutCard.setId(-1 + 2);
        ft.replace(addWorkoutCard.getId(), WorkoutCardFragment.newInstance("Add", 0, 0));
        linearLayout.addView(addWorkoutCard);

        for (int i = 1; i < workouts.size(); i++) {
            Workout workout = workouts.get(i);
            FrameLayout card = new FrameLayout(binding.getRoot().getContext());
            card.setId(i + 1);

            ft.replace(card.getId(), WorkoutCardFragment.newInstance(workout.getFormattedDate(), workout.getRecords().size(), workout.getAchievementsCount()));

            linearLayout.addView(card);
        }
        ft.commit();


        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.main_radar_chart_container, new RadarChartFragment(), null)
                .commit();


//        getChildFragmentManager().beginTransaction()
//                .setReorderingAllowed(true)
//                .add(binding.mainButtonWorkoutTest1.getId(), new WorkoutCardFragment(), null)
//                .commit();
//
//        getChildFragmentManager().beginTransaction()
//                .setReorderingAllowed(true)
//                .add(binding.mainButtonSaveWorkout.getId(), new WorkoutCardFragment(), null)
//                .commit();
//
//        getChildFragmentManager().beginTransaction()
//                .setReorderingAllowed(true)
//                .add(binding.mainButtonAllWorkouts.getId(), new WorkoutCardFragment(), null)
//                .commit();

        return binding.getRoot();
    }
}