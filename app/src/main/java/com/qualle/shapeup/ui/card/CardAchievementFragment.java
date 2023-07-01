package com.qualle.shapeup.ui.card;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qualle.shapeup.databinding.FragmentCardAchievementBinding;

public class CardAchievementFragment extends Fragment {

    private static final String ARG_NAME = "name";
    private static final String ARG_COUNT = "count";

    private String name;
    private int count;

    private FragmentCardAchievementBinding binding;

    public CardAchievementFragment() {
    }

    public static CardAchievementFragment newInstance(String name, int count) {
        CardAchievementFragment fragment = new CardAchievementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putInt(ARG_COUNT, count);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME);
            count = getArguments().getInt(ARG_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCardAchievementBinding.inflate(inflater, container, false);

        binding.achievementCardUpperText.setText(name);
        binding.achievementCardLowerText.setText(count + " Records");

        return binding.getRoot();
    }
}