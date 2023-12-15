package com.qualle.truegain.ui.chart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.qualle.truegain.R;
import com.qualle.truegain.databinding.FragmentChartLineBinding;
import com.qualle.truegain.model.enums.ChartType;
import com.qualle.truegain.util.ChartValueFormatter;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


public class ChartLineFragment extends Fragment {

    private static final String ARG_DATA = "data";
    private static final String ARG_TYPE = "type";

    private FragmentChartLineBinding binding;

    private ChartType type;
    private ArrayList<Entry> data;

    public ChartLineFragment() {
    }

    public static ChartLineFragment newInstance(Map<Float, Float> data, ChartType type) {
        ChartLineFragment fragment = new ChartLineFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, (Serializable) data);
        args.putSerializable(ARG_TYPE, type != null ? type : ChartType.SECONDARY);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = (ChartType) getArguments().getSerializable(ARG_TYPE);
            data = convertData((Map<Float, Float>) getArguments().getSerializable(ARG_DATA));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChartLineBinding.inflate(inflater, container, false);

        LineChart chart = binding.lineChart;

        chart.getDescription().setEnabled(false);

        chart.setTouchEnabled(true);

        chart.setDragEnabled(true);
        chart.setScaleXEnabled(true);
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(300);

        XAxis x = chart.getXAxis();
        x.setEnabled(true);
        x.setDrawGridLines(false);
        x.setAxisLineColor(getResources().getColor(R.color.black_russian));
        x.setTextColor(getResources().getColor(R.color.black_russian));
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setValueFormatter(ChartValueFormatter.getDateDayValueFormatter());
        x.setLabelCount(4);

        YAxis y = chart.getAxisLeft();
        y.setDrawGridLines(false);
        y.setAxisLineColor(getResources().getColor(R.color.black_russian));
        y.setTextColor(getResources().getColor(R.color.black_russian));
        y.setAxisMinimum(0f);
        y.setLabelCount(4);

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);

        chart.animateX(1000);

        chart.invalidate();

        setData(chart, data);

        return binding.getRoot();
    }


    private void setData(LineChart chart, ArrayList<Entry> values) {

        LineDataSet set1;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "Data");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(true);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(2.3f);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);

            set1.setFillFormatter((dataSet, dataProvider) -> chart.getAxisLeft().getAxisMinimum());

            if (ChartType.PRIMARY.equals(type)) {
                set1.setCircleColor(getResources().getColor(R.color.black_russian));
                set1.setColor(getResources().getColor(R.color.black_russian));
                set1.setFillColor(getResources().getColor(R.color.black_russian));
            } else {
                set1.setCircleColor(getResources().getColor(R.color.twine));
                set1.setColor(getResources().getColor(R.color.twine));
                set1.setFillColor(getResources().getColor(R.color.twine));
            }



            LineData data = new LineData(set1);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            chart.setData(data);
        }
    }

    private List<Entry> getData() {

        int count = 10;
        float range = 100;

        List<Float> dates = new ArrayList<>();

        Instant hundredYearsAgo = Instant.now().minus(Duration.ofDays(65));
        Instant tenDaysAgo = Instant.now().minus(Duration.ofDays(10));

        for (int i = 0; i < count; i++) {

            long daySinceStart = between(hundredYearsAgo, tenDaysAgo).getEpochSecond() / 86400;

            dates.add((float) daySinceStart);
        }

        dates = dates.stream().sorted(Float::compare).collect(Collectors.toList());


        List<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range + 1)) + 20;
            values.add(new Entry(dates.get(i), val));
        }

        return values;
    }

    private ArrayList<Entry> convertData(Map<Float, Float> data) {
        ArrayList<Entry> values = new ArrayList<>();

        if (data == null) { // todo throw exception
            return null;
        }

        data.forEach((k, v) -> {
            values.add(new Entry(k, v));
        });
        return values;
    }

    public static Instant between(Instant startInclusive, Instant endExclusive) {
        long startSeconds = startInclusive.getEpochSecond();
        long endSeconds = endExclusive.getEpochSecond();
        long random = ThreadLocalRandom
                .current()
                .nextLong(startSeconds, endSeconds);

        return Instant.ofEpochSecond(random);
    }

}