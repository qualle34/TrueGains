package com.qualle.truegain.ui.chart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.qualle.truegain.R;
import com.qualle.truegain.client.api.MuscleDistributionChart;
import com.qualle.truegain.databinding.FragmentChartPieBinding;
import com.qualle.truegain.databinding.FragmentChartRadarBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartRadarFragment extends Fragment {

    private static final String ARG_DATA = "data";

    private FragmentChartRadarBinding binding;

    private Map<Float, String> labels;
    private List<RadarEntry> data;

    private ChartRadarFragment() {
    }

    public static ChartRadarFragment newInstance(MuscleDistributionChart data) {
        ChartRadarFragment fragment = new ChartRadarFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setData((MuscleDistributionChart) getArguments().getSerializable(ARG_DATA));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChartRadarBinding.inflate(inflater, container, false);
        try {
            return onCreateViewInternal();

        } catch (Exception ignored) {
            return binding.getRoot();
        }
    }

    public View onCreateViewInternal() {
        RadarChart chart = binding.radarChart;

        chart.setWebLineWidth(1f);
        chart.setWebLineWidthInner(1f);
        chart.setWebAlpha(100);
        chart.setWebColor(getResources().getColor(R.color.black_russian));

        chart.getDescription().setEnabled(false);

        chart.getLegend().setEnabled(false);

        XAxis x = chart.getXAxis();
        x.setTextSize(10f);
        x.setValueFormatter(new ValueFormatter() {
            public String getFormattedValue(float value) {
                return getLabelName(value);
            }
        });

        YAxis y = chart.getYAxis();
        y.setLabelCount(6, true);
        y.setDrawLabels(false);


        chart.setData(getData());
        chart.invalidate();
        chart.animateXY(1400, 1400, Easing.EaseInOutQuad);

        return binding.getRoot();
    }

    private RadarData getData() {

        RadarDataSet set1 = new RadarDataSet(data, "Data");
        set1.setColor(getResources().getColor(R.color.twine));
        set1.setFillColor(getResources().getColor(R.color.twine));
        set1.setDrawFilled(true);
        set1.setFillAlpha(200);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);

        data.setDrawValues(false);
        data.setValueTextColor(getResources().getColor(R.color.black_russian));

        return data;
    }

    private void setData(MuscleDistributionChart distributionChart) {

        data = getEmptyArray();

        if (distributionChart == null
                || distributionChart.getThisMonthData() == null
                || distributionChart.getThisMonthData().isEmpty()) {
            return;
        }

        for (Map.Entry<String, Float> entry : distributionChart.getThisMonthData().entrySet()) {
            data.set(getLabelId(entry.getKey()), new RadarEntry(entry.getValue()));
        }

    }

    // todo
    private int getLabelId(String category) {

        switch (category.toLowerCase()) {
            case "chest":
                return 0;
            case "back":
            case "trapezius":
                return 1;
            case "biceps":
            case "triceps":
                return 2;
            case "shoulders":
                return 3;
            case "quads":
            case "hamstrings":
            case "glutes":
            case "calves":
                return 4;
            default:
                return 5;
        }
    }

    private String getLabelName(float id) {
        switch ((int) id) {
            case 0:
                return "Chest";
            case 1:
                return "Back";
            case 2:
                return "Arms";
            case 3:
                return "Shoulders";
            case 4:
                return "Legs";
            case 5:
            default:
                return "Others";
        }
    }

    private List<RadarEntry> getEmptyArray() {

        List<RadarEntry> d = new ArrayList<>();
        d.add(new RadarEntry(0));
        d.add(new RadarEntry(0));
        d.add(new RadarEntry(0));
        d.add(new RadarEntry(0));
        d.add(new RadarEntry(0));
        d.add(new RadarEntry(0));
        return d;
    }
}