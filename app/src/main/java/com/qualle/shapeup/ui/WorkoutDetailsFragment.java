package com.qualle.shapeup.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.qualle.shapeup.R;
import com.qualle.shapeup.client.InMemoryBackendClient;
import com.qualle.shapeup.client.api.Exercise;
import com.qualle.shapeup.client.api.RecordSummary;
import com.qualle.shapeup.client.api.Workout;
import com.qualle.shapeup.databinding.FragmentWorkoutDetailsBinding;
import com.qualle.shapeup.ui.adapter.ExerciseVolumeRecyclerViewAdapter;
import com.qualle.shapeup.ui.card.CardAchievementFragment;
import com.qualle.shapeup.ui.card.CardExerciseFragment;
import com.qualle.shapeup.ui.card.CardWorkoutFragment;
import com.qualle.shapeup.ui.chart.ChartBarFragment;
import com.qualle.shapeup.ui.chart.ChartPieFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkoutDetailsFragment extends Fragment {

    private static final String ARG_ID = "id";

    private long id;

    private FragmentWorkoutDetailsBinding binding;

    public WorkoutDetailsFragment() {
    }

    public static WorkoutDetailsFragment newInstance(long id) {
        WorkoutDetailsFragment fragment = new WorkoutDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWorkoutDetailsBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        binding.workoutButtonBack.setOnClickListener(v -> navController.popBackStack());

        RecyclerView recyclerView = binding.workoutVolumeRecyclerView;

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL);
        staggeredGridLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        ExerciseVolumeRecyclerViewAdapter adapter = new ExerciseVolumeRecyclerViewAdapter(InMemoryBackendClient.getVolumeRecords());
        recyclerView.setAdapter(adapter);



        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.workout_chart_container, ChartPieFragment.newInstance("testChartData", " s"), null)
                .commit();




        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        LinearLayout linearLayout = binding.workoutLinearLayoutExercises;

        List<Exercise> exercises = InMemoryBackendClient.getExercises();

        for (int i = 0; i < exercises.size() && i < 8; i++) {
            Exercise exercise = exercises.get(i);
            FrameLayout card = new FrameLayout(getContext());
            card.setId(i + 20);

            ft.replace(card.getId(), new CardExerciseFragment());
            linearLayout.addView(card);
        }

        ft.commit();






        return binding.getRoot();
    }
}