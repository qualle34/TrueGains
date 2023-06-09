package com.qualle.shapeup;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.qualle.shapeup.databinding.FragmentChartBinding;
import com.qualle.shapeup.model.enums.ChartType;
import com.qualle.shapeup.util.ChartValueFormatter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;


public class ChartFragment extends Fragment {
    private static final String ARG_TITLE = "title";
    private static final String ARG_DATA = "data";
    private static final String ARG_TYPE = "type";

    private FragmentChartBinding binding;

    private String title;
    private ChartType type;
    private ArrayList<Entry> data;

    public ChartFragment() {
    }

    public static ChartFragment newInstance(String title, ChartType type, Map<Float, Float> data) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putSerializable(ARG_TYPE, type);
        args.putSerializable(ARG_DATA, (Serializable) data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            type = (ChartType) getArguments().getSerializable(ARG_TYPE);
            data = convertData((Map<Float, Float>) getArguments().getSerializable(ARG_DATA));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChartBinding.inflate(inflater, container, false);

        binding.chartTextTitle.setText(title);
        LineChart chart = binding.chart;

        // no description text
        chart.getDescription().setEnabled(false);

        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(300);

        XAxis x = chart.getXAxis();
        x.setEnabled(true);
        x.setLabelCount(4, true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(true);

        if (ChartType.DATE.equals(type)) {
            x.setValueFormatter(ChartValueFormatter.getDateValueFormatter());
        }

        YAxis y = chart.getAxisLeft();
        y.setDrawGridLines(false);
        y.setGranularity(1f);
        y.setAxisMinimum(0f);
        y.setAxisMaximum(7f);

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);

        chart.animateXY(2000, 2000);

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
            set1 = new LineDataSet(values, "DataSet");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.05f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(true);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(2f);
            set1.setCircleColor(Color.WHITE);
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);

            set1.setFillFormatter((dataSet, dataProvider) -> chart.getAxisLeft().getAxisMinimum());

            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            chart.setData(data);
        }
    }

    private ArrayList<Entry> getData() {

        int count = 10;
        float range = 100;

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range + 1)) + 20;
            values.add(new Entry(i, val));
        }

        return values;
    }

    private ArrayList<Entry> convertData(Map<Float, Float> data) {
        ArrayList<Entry> values = new ArrayList<>();
        getData();
        if (data == null) { // todo throw exception
            return getData();
        }

        data.forEach((k, v) -> {
            values.add(new Entry(k, v));
        });
        return values;
    }

}