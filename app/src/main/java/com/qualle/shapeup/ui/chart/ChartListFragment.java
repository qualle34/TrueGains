package com.qualle.shapeup.ui.chart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.qualle.shapeup.R;
import com.qualle.shapeup.model.enums.ChartType;

import java.util.ArrayList;
import java.util.List;

public class ChartListFragment extends Fragment {

    private static final String ARG_TYPE = "type";
    private ChartListType type;

    public ChartListFragment() {
    }

    public static ChartLineFragment newInstance(ChartListType type) {
        ChartLineFragment fragment = new ChartLineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type.name());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = ChartListType.valueOf(getArguments().getString(ARG_TYPE));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_list, container, false);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        LinearLayout linearLayout = view.findViewById(R.id.chart_list_container);
        List<String> s = new ArrayList<>();


        if (ChartListType.BASE.equals(type)) {
            s.add("first");
            s.add("second");
            s.add("third");
        }

        if (!ChartListType.BASE.equals(type)) {
            s.add("first");
        }

        for (int i = 0; i < s.size(); i++) {
            FrameLayout chart = new FrameLayout(view.getContext());
            chart.setId(i + 1);

            ft.replace(chart.getId(), ChartLineFragment.newInstance("Bench press", ChartType.NUMBER, null));

            linearLayout.addView(chart);
        }

        ft.commit();

        return view;
    }

    public enum ChartListType {
        SIZE,
        BASE,
        BACK,
        CHEST,
        BICEPS,
        TRICEPS,
        SHOULDERS,
        LEGS
    }
}