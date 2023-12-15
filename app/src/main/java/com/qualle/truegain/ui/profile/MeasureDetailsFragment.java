package com.qualle.truegain.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.R;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.Measure;
import com.qualle.truegain.client.api.UserMeasure;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentMeasureDetailsBinding;
import com.qualle.truegain.model.enums.ChartType;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.ui.adapter.UserMeasureRecyclerViewAdapter;
import com.qualle.truegain.ui.chart.ChartLineFragment;
import com.qualle.truegain.util.DateFormatterUtil;

import java.time.LocalDateTime;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeasureDetailsFragment extends Fragment {

    private static final String ARG_ID = "id";

    private long id;

    private FragmentMeasureDetailsBinding binding;

    @Inject
    public LocalService service;

    @Inject
    public BackendClient client;

    @Inject
    public AuthenticationHandler authenticationHandler;

    public MeasureDetailsFragment() {
    }

    public static MeasureDetailsFragment newInstance(long id) {
        MeasureDetailsFragment fragment = new MeasureDetailsFragment();
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
        binding = FragmentMeasureDetailsBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);
        DaggerApplicationComponent.builder()
                .clientModule(ClientModule.getInstance(getContext())).build()
                .inject(this);

        try {
            authenticationHandler.holdAuthentication();
        } catch (Exception e) {
            navController.navigate(R.id.action_nav_measure_details_fragment_to_nav_greeting_fragment);
            return binding.getRoot();
        }

        binding.workoutButtonBack.setOnClickListener(v -> navController.popBackStack());

        Context context = binding.getRoot().getContext();
        RecyclerView recyclerView = binding.measureRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        client.getMeasureByUser(service.getAuthorizationHeader(), id).enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<Measure> call, Response<Measure> response) {

                if (response.isSuccessful()) {

                    Measure dto = response.body();
                    binding.measureTitle.setText(dto.getName());

                    getChildFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .add(binding.measureChartContainer.getId(), ChartLineFragment.newInstance(dto.getData(), ChartType.PRIMARY), null)
                            .commit();

                    recyclerView.setAdapter(new UserMeasureRecyclerViewAdapter(dto));
                }
            }

            @Override
            public void onFailure(Call<Measure> call, Throwable t) {
                t.printStackTrace();
            }
        });

        LocalDateTime now = LocalDateTime.now();
        binding.measureCurrentDate.setText(DateFormatterUtil.formatToSimpleDate(now));

        binding.measureSave.setOnClickListener(v -> {

            float val = Float.parseFloat(binding.measureValue.getText().toString());

            UserMeasure measure = new UserMeasure();
            measure.setMeasureId(id);
            measure.setDate(DateFormatterUtil.toApiDate(now));
            measure.setValue(val);

            client.saveMeasure(service.getAuthorizationHeader(), id, measure).enqueue(new Callback<Measure>() {
                @Override
                public void onResponse(Call<Measure> call, Response<Measure> response) {

                }

                @Override
                public void onFailure(Call<Measure> call, Throwable t) {
                }
            });

            ((UserMeasureRecyclerViewAdapter) recyclerView.getAdapter())
                    .addItem(DateFormatterUtil.dateToDayNumber(now), val);
            ((UserMeasureRecyclerViewAdapter) recyclerView.getAdapter())
                    .notifyDataSetChanged();

            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);

        });

        return binding.getRoot();
    }
}