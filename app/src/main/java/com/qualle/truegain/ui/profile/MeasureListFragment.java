package com.qualle.truegain.ui.profile;

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
import com.qualle.truegain.client.api.Measure;
import com.qualle.truegain.client.api.SimpleWorkout;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentMeasureListBinding;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.ui.adapter.MeasureRecyclerViewAdapter;
import com.qualle.truegain.ui.adapter.WorkoutListRecyclerViewAdapter;
import com.qualle.truegain.ui.listener.MeasureListClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeasureListFragment extends Fragment implements MeasureListClickListener {


    private FragmentMeasureListBinding binding;

    @Inject
    public LocalService service;

    @Inject
    public BackendClient client;

    @Inject
    public AuthenticationHandler authenticationHandler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMeasureListBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);
        DaggerApplicationComponent.builder()
                .clientModule(ClientModule.getInstance(getContext())).build()
                .inject(this);

        MeasureListFragment fragment = this;

        try {
            authenticationHandler.holdAuthentication();
        } catch (Exception e) {
            navController.navigate(R.id.action_nav_measure_list_fragment_to_nav_greeting_fragment);
            return binding.getRoot();
        }

        binding.measureListButtonBack.setOnClickListener(v -> navController.popBackStack());

        Context context = binding.getRoot().getContext();
        RecyclerView recyclerView = binding.measureRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        // todo
        client.getMeasures().enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<List<Measure>> call, Response<List<Measure>> response) {
                if (response.isSuccessful()) {
                    List<Measure> dto = response.body();

                    recyclerView.setAdapter(new MeasureRecyclerViewAdapter(dto, fragment));
                }
            }

            @Override
            public void onFailure(Call<List<Measure>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onMeasureClick(long measureId) {
        NavController navController = NavHostFragment.findNavController(this);

        Bundle bundle = new Bundle();
        bundle.putLong("id", measureId);

        navController.navigate(R.id.action_nav_measure_list_fragment_to_nav_measure_details_fragment, bundle);
    }
}