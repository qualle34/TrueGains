package com.qualle.shapeup.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.shapeup.R;
import com.qualle.shapeup.client.InMemoryBackendClient;
import com.qualle.shapeup.databinding.FragmentWorkoutListBinding;
import com.qualle.shapeup.ui.adapter.WorkoutListRecyclerViewAdapter;
import com.qualle.shapeup.ui.listener.WorkoutListClickListener;

public class WorkoutListFragment extends Fragment implements WorkoutListClickListener {

    private FragmentWorkoutListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWorkoutListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Context context = view.getContext();
        RecyclerView recyclerView = binding.workoutListRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(new WorkoutListRecyclerViewAdapter(this, InMemoryBackendClient.getWorkouts()));


        return view;
    }


    @Override
    public void onWorkoutClick(long workoutId) {
        NavController navController = NavHostFragment.findNavController(this);

        Bundle args = new Bundle();
        args.putLong("id", workoutId);

        navController.navigate(R.id.action_nav_workout_list_fragment_to_nav_workout_details_fragment, args);
    }
}