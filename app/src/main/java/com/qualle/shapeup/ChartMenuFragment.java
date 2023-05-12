package com.qualle.shapeup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class ChartMenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chart_menu, container, false);

        view.findViewById(R.id.chart_menu_button_size).setOnClickListener(buildListener(ChartListFragment.ChartListType.SIZE));
        view.findViewById(R.id.chart_menu_button_base).setOnClickListener(buildListener(ChartListFragment.ChartListType.BASE));
        view.findViewById(R.id.chart_menu_button_chest).setOnClickListener(buildListener(ChartListFragment.ChartListType.CHEST));
        view.findViewById(R.id.chart_menu_button_back).setOnClickListener(buildListener(ChartListFragment.ChartListType.BACK));
        view.findViewById(R.id.chart_menu_button_biceps).setOnClickListener(buildListener(ChartListFragment.ChartListType.BICEPS));
        view.findViewById(R.id.chart_menu_button_triceps).setOnClickListener(buildListener(ChartListFragment.ChartListType.TRICEPS));
        view.findViewById(R.id.chart_menu_button_shoulders).setOnClickListener(buildListener(ChartListFragment.ChartListType.SHOULDERS));
        view.findViewById(R.id.chart_menu_button_legs).setOnClickListener(buildListener(ChartListFragment.ChartListType.LEGS));

        return view;
    }

    private View.OnClickListener buildListener(ChartListFragment.ChartListType type) {

        NavController navController = NavHostFragment.findNavController(this);

        Bundle bundle = new Bundle();

        return (v -> {
            bundle.putString("type", type.name());
            navController.navigate(R.id.action_nav_chart_menu_fragment_to_chartListFragment, bundle);
        }
        );
    }
}