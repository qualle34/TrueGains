package com.qualle.truegain.ui.workout;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.qualle.truegain.R;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.Exercise;
import com.qualle.truegain.client.api.Record;
import com.qualle.truegain.client.api.User;
import com.qualle.truegain.client.api.Workout;
import com.qualle.truegain.client.api.WorkoutVolume;
import com.qualle.truegain.config.ApplicationComponent;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentWorkoutDetailsBinding;
import com.qualle.truegain.model.local.ExerciseDetailsProto;
import com.qualle.truegain.model.local.VolumeProto;
import com.qualle.truegain.model.local.WorkoutDetailsProto;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.ui.adapter.ExerciseVolumeRecyclerViewAdapter;
import com.qualle.truegain.ui.card.CardExerciseFragment;
import com.qualle.truegain.ui.chart.ChartPieFragment;
import com.qualle.truegain.util.DateFormatterUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutDetailsFragment extends Fragment {

    private static final String ARG_ID = "id";

    private long id;

    private FragmentWorkoutDetailsBinding binding;

    @Inject
    public LocalService service;

    @Inject
    public BackendClient client;

    @Inject
    public AuthenticationHandler authenticationHandler;

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
        DaggerApplicationComponent.builder()
                .clientModule(ClientModule.getInstance(getContext())).build()
                .inject(this);

        if (authenticationHandler.isAuthenticationRequired()) {
            navController.navigate(R.id.action_nav_workout_details_fragment_to_nav_greeting_fragment);
            return binding.getRoot();
        }

        if (authenticationHandler.isRefreshRequired()) {
            authenticationHandler.refresh();
        }

        binding.workoutButtonBack.setOnClickListener(v -> navController.popBackStack());

        client.getWorkoutById(service.getAuthorizationHeader(), id).enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<Workout> call, Response<Workout> response) {

                if (response.isSuccessful()) {
                    Workout dto = response.body();

                    binding.workoutDate.setText(DateFormatterUtil.formatToSimpleDate(DateFormatterUtil.fromApiDate(dto.getDate())));
                    binding.workoutDetailsExerciseCount.setText((dto.getExercises() != null ? dto.getExercises().size() : 0) + " Exercises");

                    List<WorkoutVolume> volumeForExercises = dto.getVolumeForExercises();
                    RecyclerView recyclerView = binding.workoutVolumeRecyclerView;
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(getSpanCount(volumeForExercises.size()), LinearLayoutManager.HORIZONTAL);
                    staggeredGridLayoutManager.setAutoMeasureEnabled(true);
                    recyclerView.setLayoutManager(staggeredGridLayoutManager);

                    ExerciseVolumeRecyclerViewAdapter adapter = new ExerciseVolumeRecyclerViewAdapter(volumeForExercises);
                    recyclerView.setAdapter(adapter);


                    getChildFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.workout_chart_container, ChartPieFragment.newInstance(dto.getVolumeForBodyParts()), null)
                            .commit();

                    List<Exercise> exercises = dto.getExercises();

                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    LinearLayout linearLayout = binding.workoutLinearLayoutExercises;

                    for (int i = 0; i < exercises.size(); i++) {
                        FrameLayout card = new FrameLayout(getContext());
                        card.setId(i + 20);

                        ft.add(card.getId(), CardExerciseFragment.newInstance(exercises.get(i)));
                        linearLayout.addView(card);
                    }

                    ft.commit();

                }
            }

            @Override
            public void onFailure(Call<Workout> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return binding.getRoot();
    }

    private int getSpanCount(int size) {

        if (size < 3) {
            return 1;
        } else if (size < 6) {
            return 2;
        } else {
            return 3;
        }
    }
}