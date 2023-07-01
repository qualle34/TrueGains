package com.qualle.shapeup.ui.card;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qualle.shapeup.databinding.FragmentCardWorkoutBinding;

public class CardWorkoutFragment extends Fragment {

    private static final String ARG_DATE = "date";
    private static final String ARG_RECORDS = "records";
    private static final String ARG_ACHIEVEMENTS = "achievements";

    private FragmentCardWorkoutBinding binding;

    private String date;
    private int records;
    private int achievements;

    public CardWorkoutFragment() {
    }

    public static CardWorkoutFragment newInstance(String date, int records, int achievements) {
        CardWorkoutFragment fragment = new CardWorkoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DATE, date);
        args.putSerializable(ARG_RECORDS, records);
        args.putSerializable(ARG_ACHIEVEMENTS, achievements);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            date = getArguments().getString(ARG_DATE);
            records = getArguments().getInt(ARG_RECORDS);
            achievements = getArguments().getInt(ARG_ACHIEVEMENTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCardWorkoutBinding.inflate(inflater, container, false);

        binding.workoutCardUpperText.setText(date);
        binding.workoutCardMiddleText.setText(records + " Exercises");
        binding.workoutCardLowerText.setText(achievements + " new achievements");

        return binding.getRoot();
    }
}