package com.qualle.shapeup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.qualle.shapeup.databinding.FragmentExerciseMenuBinding;

public class ExerciseMenuFragment extends Fragment {

    private FragmentExerciseMenuBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExerciseMenuBinding.inflate(inflater, container, false);

        binding.chartMenuButtonSize.setOnClickListener(buildListener(ChartListFragment.ChartListType.SIZE));
        binding.chartMenuButtonBase.setOnClickListener(buildListener(ChartListFragment.ChartListType.BASE));
        binding.chartMenuButtonChest.setOnClickListener(buildListener(ChartListFragment.ChartListType.CHEST));
        binding.chartMenuButtonBack.setOnClickListener(buildListener(ChartListFragment.ChartListType.BACK));
        binding.chartMenuButtonBiceps.setOnClickListener(buildListener(ChartListFragment.ChartListType.BICEPS));
        binding.chartMenuButtonTriceps.setOnClickListener(buildListener(ChartListFragment.ChartListType.TRICEPS));
        binding.chartMenuButtonShoulders.setOnClickListener(buildListener(ChartListFragment.ChartListType.SHOULDERS));
        binding.chartMenuButtonLegs.setOnClickListener(buildListener(ChartListFragment.ChartListType.LEGS));

        return binding.getRoot();
    }

    private View.OnClickListener buildListener(ChartListFragment.ChartListType type) {
        NavController navController = NavHostFragment.findNavController(this);

        Bundle bundle = new Bundle();

        return (v -> {
            bundle.putString("type", type.name());
            navController.navigate(R.id.action_nav_chart_menu_fragment_to_nav_chart_list_fragment, bundle);
        }
        );
    }
}