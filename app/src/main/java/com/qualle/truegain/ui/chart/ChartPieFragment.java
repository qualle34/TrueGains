package com.qualle.truegain.ui.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.MPPointF;
import com.qualle.truegain.R;
import com.qualle.truegain.client.api.WorkoutVolume;
import com.qualle.truegain.databinding.FragmentChartPieBinding;
import com.qualle.truegain.model.local.VolumeProto;
import com.qualle.truegain.util.PieChartFormatter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChartPieFragment extends Fragment {

    private static final String ARG_DATA = "data";

    private List<WorkoutVolume> volume;

    private FragmentChartPieBinding binding;

    public ChartPieFragment() {
    }

    public static Fragment newInstance(List<WorkoutVolume> volumeForExercises) {
        ChartPieFragment fragment = new ChartPieFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, (Serializable) volumeForExercises);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            volume = (List<WorkoutVolume>) getArguments().getSerializable(ARG_DATA);
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


        setData(chart);

        return binding.getRoot();
    }

    private void setData(PieChart chart) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < volume.size(); i++) {
            entries.add(new PieEntry((float) volume.get(i).getValue(), volume.get(i).getName()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 60));
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