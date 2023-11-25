package com.qualle.truegain.ui.menu;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.qualle.truegain.R;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.Exercise;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentExerciseDetailedBinding;
import com.qualle.truegain.model.enums.ChartType;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.ui.chart.ChartLineFragment;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseDetailedFragment extends Fragment {

    private static final String ARG_ID = "id";

    private FragmentExerciseDetailedBinding binding;

    @Inject
    public LocalService service;

    @Inject
    public AuthenticationHandler authenticationHandler;

    @Inject
    public BackendClient client;

    private long id;

    public ExerciseDetailedFragment() {
    }

    public static ExerciseDetailedFragment newInstance(long id) {
        ExerciseDetailedFragment fragment = new ExerciseDetailedFragment();
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
        binding = FragmentExerciseDetailedBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        DaggerApplicationComponent.builder()
                .clientModule(ClientModule.getInstance(getContext())).build()
                .inject(this);

        if (authenticationHandler.isAuthenticationRequired()) {
            navController.navigate(R.id.action_nav_exercise_detailed_fragment_to_nav_greeting_fragment);
            return binding.getRoot();
        }

        if (authenticationHandler.isRefreshRequired()) {
            authenticationHandler.refresh();
        }

        binding.exerciseButtonBack.setOnClickListener(v -> navController.popBackStack());

        client.getExerciseWithChartData(service.getAuthorizationHeader(), id).enqueue(new Callback<Exercise>() {

            @Override
            public void onResponse(Call<Exercise> call, Response<Exercise> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }

                Exercise exercise = response.body();

                binding.exerciseEquipment.setText(exercise.getEquipment());
                binding.exerciseTitle.setText(exercise.getName());
                binding.exerciseSummary.setText(exercise.getSummary());
                binding.exerciseDescription.setText(exercise.getDescription());
                binding.exerciseTechnique.setText(exercise.getTechnique().replace("\\n", "\n"));

                try {
                    AssetManager manager = getContext().getAssets();

                    if (manager != null) {

                        InputStream ims = manager.open("image_barbell_bench_press.png");
                        Drawable d = Drawable.createFromStream(ims, null);
                        binding.exerciseImageView.setImageDrawable(d);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (exercise.getLoadData() != null && !exercise.getLoadData().isEmpty()) {
                    binding.exerciseChartContainerSecondary.setVisibility(View.VISIBLE);
                    binding.exerciseChartTitleSecondary.setVisibility(View.VISIBLE);

                    getChildFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .add(binding.exerciseChartContainerPrimary.getId(), ChartLineFragment.newInstance(exercise.getLoadData(), ChartType.PRIMARY), null)
                            .commit();
                } else {
                    binding.exerciseChartContainerPrimary.setVisibility(View.GONE);
                    binding.exerciseChartTitlePrimary.setVisibility(View.GONE);
                }

                if (exercise.getMaxRepData() != null && !exercise.getMaxRepData().isEmpty()) {
                    binding.exerciseChartContainerSecondary.setVisibility(View.VISIBLE);
                    binding.exerciseChartTitleSecondary.setVisibility(View.VISIBLE);

                    getChildFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .add(binding.exerciseChartContainerSecondary.getId(), ChartLineFragment.newInstance(exercise.getMaxRepData(), ChartType.SECONDARY), null)
                            .commit();
                } else {
                    binding.exerciseChartContainerSecondary.setVisibility(View.GONE);
                    binding.exerciseChartTitleSecondary.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Exercise> call, Throwable t) {
                t.printStackTrace();
            }
        });


        return binding.getRoot();
    }
}