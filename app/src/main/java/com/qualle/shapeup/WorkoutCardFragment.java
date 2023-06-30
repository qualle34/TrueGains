package com.qualle.shapeup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.Entry;
import com.qualle.shapeup.databinding.FragmentChartBinding;
import com.qualle.shapeup.databinding.FragmentWorkoutCardBinding;
import com.qualle.shapeup.model.enums.ChartType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class WorkoutCardFragment extends Fragment {

    private static final String ARG_DATE = "date";
    private static final String ARG_RECORDS = "records";
    private static final String ARG_ACHIEVEMENTS = "achievements";

    private FragmentWorkoutCardBinding binding;

    private String date;
    private int records;
    private int achievements;

    public WorkoutCardFragment() {
    }

    public static WorkoutCardFragment newInstance(String date, int records, int achievements) {
        WorkoutCardFragment fragment = new WorkoutCardFragment();
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
        binding = FragmentWorkoutCardBinding.inflate(inflater, container, false);

        binding.workoutCardUpperText.setText(date);
        binding.workoutCardMiddleText.setText(records + " Excercises");
        binding.workoutCardLowerText.setText(achievements + " new achievements");

        return binding.getRoot();
    }
}