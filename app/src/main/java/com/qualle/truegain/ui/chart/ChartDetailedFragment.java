package com.qualle.truegain.ui.chart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.qualle.truegain.databinding.FragmentChartDetailedBinding;
import com.qualle.truegain.model.enums.ChartType;
import com.qualle.truegain.service.LocalService;

public class ChartDetailedFragment extends Fragment {

    private static final String ARG_ID = "id";

    private FragmentChartDetailedBinding binding;
    private LocalService service;


    private long id;

    public ChartDetailedFragment() {
    }

    public static ChartDetailedFragment newInstance(long id) {
        ChartDetailedFragment fragment = new ChartDetailedFragment();
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
        binding = FragmentChartDetailedBinding.inflate(inflater, container, false);
        service = LocalService.getInstance(getContext());



        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(binding.mainChartContainer.getId(), ChartLineFragment.newInstance("Bench Press", ChartType.NUMBER, null), null)
                .commit();


        return binding.getRoot();
    }
}