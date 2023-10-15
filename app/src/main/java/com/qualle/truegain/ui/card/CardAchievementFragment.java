package com.qualle.truegain.ui.card;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qualle.truegain.R;
import com.qualle.truegain.databinding.FragmentCardChartBinding;

public class CardAchievementFragment extends Fragment {

    private static final String ARG_ID = "id";
    private static final String ARG_NAME = "name";
    private static final String ARG_COUNT = "count";

    private long id;
    private String name;
    private int count;

    private FragmentCardChartBinding binding;

    public CardAchievementFragment() {
    }

    public static CardAchievementFragment newInstance(long id, String name, int count) {
        CardAchievementFragment fragment = new CardAchievementFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID, id);
        args.putString(ARG_NAME, name);
        args.putInt(ARG_COUNT, count);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong(ARG_ID);
            name = getArguments().getString(ARG_NAME);
            count = getArguments().getInt(ARG_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCardChartBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        binding.chartCardUpperText.setText(name);
        binding.chartCardLowerText.setText(count + " Records");

        Bundle bundle = new Bundle();
        bundle.putLong(ARG_ID, id);

        binding.mainChartCard.setOnClickListener(v -> {
            navController.navigate(R.id.action_nav_main_fragment_to_nav_chart_detailed_fragment, bundle);
        });

        return binding.getRoot();
    }
}