package com.qualle.shapeup.ui.chart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.qualle.shapeup.R;
import com.qualle.shapeup.databinding.FragmentChartPieBinding;
import com.qualle.shapeup.util.PieChartFormatter;

import java.util.ArrayList;

public class ChartPieFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    protected final String[] parties = new String[] {
            "Shoulders", "Back", "Chest", "Biceps", "Triceps"
    };

    private String mParam1;
    private String mParam2;

    private FragmentChartPieBinding binding;

    public ChartPieFragment() {
    }

    public static ChartPieFragment newInstance(String param1, String param2) {
        ChartPieFragment fragment = new ChartPieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChartPieBinding.inflate(inflater, container, false);

        PieChart chart = binding.pieChart;
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);

        chart.setDragDecelerationFrictionCoef(0.75f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(getResources().getColor(R.color.pearl_bush));

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(35f);
        chart.setTransparentCircleRadius(38f);

        chart.setDrawCenterText(false);

        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);



        chart.animateY(1000, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setEnabled(false);

        chart.setEntryLabelColor(getResources().getColor(R.color.zircon));
        chart.setEntryLabelTextSize(12f);


        setData(chart, 5, 30);

        return binding.getRoot();
    }

    private void setData(PieChart chart, int count, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * range) + range / 5),
                    parties[i % parties.length]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(getResources().getColor(R.color.black_russian));
        colors.add(getResources().getColor(R.color.twine));

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PieChartFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(getResources().getColor(R.color.zircon));
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }
}