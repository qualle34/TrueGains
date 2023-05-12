package com.qualle.shapeup;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.qualle.shapeup.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.List;

public class ChartListFragment extends Fragment {

    private static final String ARG_TYPE = "type";
    private ChartListType type;

    public ChartListFragment() {
    }

    public static ChartFragment newInstance(ChartListType type) {
        ChartFragment fragment = new ChartFragment();
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

            ft.replace(chart.getId(), ChartFragment.newInstance(i));

            linearLayout.addView(chart);
        }

        ft.commit();

        return view;
    }

    static enum ChartListType {
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