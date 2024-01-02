package com.qualle.truegain.ui.chart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.qualle.truegain.R;
import com.qualle.truegain.client.api.WorkoutPerWeek;
import com.qualle.truegain.databinding.FragmentChartBarBinding;
import com.qualle.truegain.util.ChartValueFormatter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChartBarFragment extends Fragment {

    private static final String ARG_DATA = "data";

    private FragmentChartBarBinding binding;

    private ArrayList<BarEntry> data;

    public ChartBarFragment() {
    }

    public static ChartBarFragment newInstance(List<WorkoutPerWeek> data) {
        ChartBarFragment fragment = new ChartBarFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, (Serializable) data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = convertData((List<WorkoutPerWeek>) getArguments().getSerializable(ARG_DATA));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChartBarBinding.inflate(inflater, container, false);

        BarChart chart = binding.barChart;
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);

        chart.setTouchEnabled(true);

        chart.setDragEnabled(true);
        chart.setScaleXEnabled(true);
        chart.setPinchZoom(false);

        chart.setPinchZoom(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);
        chart.setDrawValueAboveBar(true);

        chart.setMaxVisibleValueCount(5);

        XAxis x = chart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setValueFormatter(ChartValueFormatter.getDateValueFormatter());
        x.setAxisLineColor(getResources().getColor(R.color.black_russian));
        x.setLabelCount(4);
        x.setTextSize(9f);

        chart.getAxisRight().setEnabled(false);

        YAxis y = chart.getAxisLeft();
        y.setDrawLabels(false);
        y.setDrawGridLines(false);
        y.setGranularity(1f);
        y.setAxisLineColor(getResources().getColor(R.color.black_russian));
        y.setAxisMinimum(0f);

        chart.animateY(1500);

        BarDataSet set;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {

            set = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set.setValues(data);

            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set = new BarDataSet(data, "Data Set");
            set.setColors(colors());
            set.setColors(new int[]{R.color.black_russian, R.color.twine}, getContext());

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);

            BarData data = new BarData(dataSets);
            data.setBarWidth(0.5f);
            data.setValueFormatter(new DefaultValueFormatter(0));
            data.setValueTextSize(10f);
            chart.setData(data);

        }

        chart.invalidate();

        return binding.getRoot();
    }

    private ArrayList<BarEntry> convertData(List<WorkoutPerWeek> data) {

        if (data == null || data.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Integer, Integer> values = new HashMap<>();
        LocalDate startOfYear = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());

        for (WorkoutPerWeek week : data) {

            LocalDate monday = LocalDate.ofEpochDay(week.getDay());

            if (startOfYear.isBefore(monday) || startOfYear.isEqual(monday)) {

                values.put(week.getWeek(), week.getCount());

            } else {
                int weeksOfYear = Calendar.getInstance().getActualMaximum(Calendar.WEEK_OF_YEAR);

                values.put(week.getWeek() - weeksOfYear, week.getCount());
            }

        }

        ArrayList<BarEntry> result = new ArrayList<>();

        int currentWeek = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());

        for (int i = 0; i < 4; i++) {
            if (values.containsKey(currentWeek - i)) {
                result.add(new BarEntry(currentWeek - i, values.get(currentWeek - i)));
            }
        }

        return result;
    }

    private int[] colors() {
        return new int[]{
                ContextCompat.getColor(getActivity(), R.color.twine),
                ContextCompat.getColor(getActivity(), R.color.black_russian)
        };
    }
}