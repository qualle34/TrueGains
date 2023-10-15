package com.qualle.truegain.ui;

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

import com.qualle.truegain.R;
import com.qualle.truegain.client.InMemoryBackendClient;
import com.qualle.truegain.client.api.RecordSummary;
import com.qualle.truegain.databinding.FragmentMainBinding;
import com.qualle.truegain.model.local.SimpleWorkoutProto;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.ui.card.CardAchievementFragment;
import com.qualle.truegain.ui.card.CardWorkoutFragment;
import com.qualle.truegain.ui.chart.ChartBarFragment;
import com.qualle.truegain.ui.chart.ChartRadarFragment;

import java.util.List;
import java.util.Map;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private LocalService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        service = LocalService.getInstance(getContext());
        NavController navController = NavHostFragment.findNavController(this);

        binding.mainButtonProfile.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_main_fragment_to_nav_profile_fragment));

        binding.mainButtonAllWorkouts.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_main_fragment_to_workoutListFragment));

        binding.mainButtonStartWorkout.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_main_fragment_to_nav_save_workout_fragment));

        Map<Float, Float> testChartData = service.getBarChartData();

        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.main_chart_container, ChartBarFragment.newInstance(testChartData), null)
                .commit();

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        LinearLayout linearLayout = binding.mainLinearLayoutWorkout;

        if (service.isCurrentWorkoutStarted()) {
            binding.mainButtonStartWorkout.setText("Continue\nworkout");
        }


        List<SimpleWorkoutProto> workouts = service.getWorkouts();

        for (int i = 0; i < workouts.size(); i++) {
            SimpleWorkoutProto workout = workouts.get(i);
            FrameLayout card = new FrameLayout(getContext());
            card.setClickable(true);
            card.setId(i + 1);

            ft.replace(card.getId(), CardWorkoutFragment.newInstance(workout.getId(), workout.getDate(), workout.getExercisesCount(), workout.getAchievementsCount()));
            linearLayout.addView(card);
        }

        ft.commit();


        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.main_radar_chart_container, new ChartRadarFragment(), null)
                .commit();


        FragmentTransaction ft1 = getChildFragmentManager().beginTransaction();
        LinearLayout linearLayout1 = binding.mainLinearLayoutAchievements;
        List<RecordSummary> recordSummaries = InMemoryBackendClient.getRecordsGroupByExercise();

        for (int i = 0; i < recordSummaries.size(); i++) {
            RecordSummary recordSummary = recordSummaries.get(i);
            FrameLayout card = new FrameLayout(getContext());
            card.setId(i + 10);

            ft1.replace(card.getId(), CardAchievementFragment.newInstance(1, recordSummary.getExercise().getName(), recordSummary.getCount()));
            linearLayout1.addView(card);
        }

        ft1.commit();

        return binding.getRoot();
    }
}