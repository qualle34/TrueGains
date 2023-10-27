package com.qualle.truegain.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.R;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.InMemoryBackendClient;
import com.qualle.truegain.client.api.MainPageData;
import com.qualle.truegain.client.api.RecordSummary;
import com.qualle.truegain.client.api.SimpleWorkout;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentMainBinding;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.ui.adapter.MainWorkoutListRecyclerViewAdapter;
import com.qualle.truegain.ui.adapter.WorkoutListRecyclerViewAdapter;
import com.qualle.truegain.ui.card.CardAchievementFragment;
import com.qualle.truegain.ui.card.CardWorkoutFragment;
import com.qualle.truegain.ui.chart.ChartBarFragment;
import com.qualle.truegain.ui.chart.ChartRadarFragment;
import com.qualle.truegain.ui.listener.WorkoutListClickListener;
import com.qualle.truegain.ui.workout.WorkoutListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment implements WorkoutListClickListener {

    private FragmentMainBinding binding;
    private LocalService service;

    @Inject
    public BackendClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        service = LocalService.getInstance(getContext());
        Context context = binding.getRoot().getContext();
        MainFragment fragment = this;

        NavController navController = NavHostFragment.findNavController(this);
        DaggerApplicationComponent.create().inject(this);

        binding.mainButtonProfile.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_main_fragment_to_nav_profile_fragment));

        binding.mainButtonAllWorkouts.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_main_fragment_to_workoutListFragment));

        binding.mainButtonStartWorkout.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_main_fragment_to_nav_save_workout_fragment));

        if (service.isCurrentWorkoutStarted()) {
            binding.mainButtonStartWorkout.setText("Continue\nworkout");
        }

        RecyclerView recyclerView = binding.mainWorkoutListRecyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        client.getMainPageData(1).enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<MainPageData> call, Response<MainPageData> response) {
                MainPageData dto = response.body();
                List<SimpleWorkout> workouts = dto.getRecentWorkouts();

                recyclerView.setAdapter(new MainWorkoutListRecyclerViewAdapter(fragment, workouts));

                getChildFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.main_chart_container, ChartBarFragment.newInstance(dto.getWorkoutPerWeekChartData()), null)
                        .commit();

                getChildFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.main_radar_chart_container, new ChartRadarFragment(), null)
                        .commit();
            }

            @Override
            public void onFailure(Call<MainPageData> call, Throwable t) {
                t.printStackTrace();
            }
        });


        FragmentTransaction ft1 = getChildFragmentManager().beginTransaction();
        LinearLayout linearLayout1 = binding.mainLinearLayoutAchievements;
        List<RecordSummary> recordSummaries = InMemoryBackendClient.getRecordsGroupByExercise();

        for (int i = 0; i < recordSummaries.size(); i++) {
            RecordSummary recordSummary = recordSummaries.get(i);
            FrameLayout card = new FrameLayout(getContext());
            card.setId(i + 10);

            ft1.add(card.getId(), CardAchievementFragment.newInstance(1, recordSummary.getExercise().getName(), recordSummary.getCount()));
            linearLayout1.addView(card);
        }

        ft1.commit();

        return binding.getRoot();
    }

    @Override
    public void onWorkoutClick(long workoutId) {
        NavController navController = NavHostFragment.findNavController(this);

        Bundle args = new Bundle();
        args.putLong("id", workoutId);

        navController.navigate(R.id.action_nav_main_fragment_to_nav_workout_details_fragment, args);
    }
}