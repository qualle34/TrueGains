package com.qualle.truegain.ui.workout;

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

import com.qualle.truegain.R;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.SimpleWorkout;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentWorkoutListBinding;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.ui.adapter.WorkoutListRecyclerViewAdapter;
import com.qualle.truegain.ui.listener.WorkoutListClickListener;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutListFragment extends Fragment implements WorkoutListClickListener {

    private FragmentWorkoutListBinding binding;

    @Inject
    public LocalService service;

    @Inject
    public BackendClient client;

    @Inject
    public AuthenticationHandler authenticationHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWorkoutListBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);
        DaggerApplicationComponent.builder()
                .clientModule(ClientModule.getInstance(getContext())).build()
                .inject(this);
        WorkoutListFragment fragment = this;

        try {
            authenticationHandler.holdAuthentication();
        } catch (Exception e) {
            navController.navigate(R.id.action_nav_main_fragment_to_nav_greeting_fragment);
            return binding.getRoot();
        }

        binding.workoutListButtonBack.setOnClickListener(v -> navController.popBackStack());

        Context context = binding.getRoot().getContext();
        RecyclerView recyclerView = binding.workoutListRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        client.getWorkoutsByUser(service.getAuthorizationHeader()).enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<List<SimpleWorkout>> call, Response<List<SimpleWorkout>> response) {
                if (response.isSuccessful()) {
                    List<SimpleWorkout> dto = response.body();

                    recyclerView.setAdapter(new WorkoutListRecyclerViewAdapter(fragment, dto));
                }
            }

            @Override
            public void onFailure(Call<List<SimpleWorkout>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return binding.getRoot();
    }


    @Override
    public void onWorkoutClick(long workoutId) {
        NavController navController = NavHostFragment.findNavController(this);

        Bundle args = new Bundle();
        args.putLong("id", workoutId);

        navController.navigate(R.id.action_nav_workout_list_fragment_to_nav_workout_details_fragment, args);
    }
}