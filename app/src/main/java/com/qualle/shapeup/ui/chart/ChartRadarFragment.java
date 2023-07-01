package com.qualle.shapeup.ui.chart;

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
import com.qualle.shapeup.R;
import com.qualle.shapeup.databinding.FragmentChartRadarBinding;

import java.util.ArrayList;


public class ChartRadarFragment extends Fragment {

    private FragmentChartRadarBinding binding;

    public ChartRadarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChartRadarBinding.inflate(inflater, container, false);

        RadarChart chart = binding.radarChart;

        chart.setWebLineWidth(1f);
        chart.setWebLineWidthInner(1f);
        chart.setWebAlpha(100);
        chart.setWebColor(getResources().getColor(R.color.black_russian));

        chart.getDescription().setEnabled(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setXOffset(14f);
        l.setYOffset(-38f);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setTextColor(getResources().getColor(R.color.black_russian));


        XAxis x = chart.getXAxis();
        x.setTextSize(10f);
        x.setValueFormatter(new ValueFormatter() {

            private final String[] activities = new String[]{"Chest", "Back", "Legs", "Arms", "Shoulders"};

            public String getFormattedValue(float value) {
                return activities[(int) value % activities.length];
            }
        });

        YAxis y = chart.getYAxis();
        y.setLabelCount(5, true);
        y.setDrawLabels(false);


        chart.setData(getData());
        chart.invalidate();
        chart.animateXY(1400, 1400, Easing.EaseInOutQuad);


        return binding.getRoot();
    }

    private RadarData getData() {

        float mul = 80;
        float min = 20;
        int cnt = 5;

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        ArrayList<RadarEntry> entries2 = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mul) + min;
            entries1.add(new RadarEntry(val1));

            float val2 = (float) (Math.random() * mul) + min;
            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Last Month");
        set1.setColor(getResources().getColor(R.color.black_russian));
        set1.setFillColor(getResources().getColor(R.color.black_russian));
        set1.setDrawFilled(true);
        set1.setFillAlpha(200);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "This Month");
        set2.setColor(getResources().getColor(R.color.twine));
        set2.setFillColor(getResources().getColor(R.color.twine));
        set2.setDrawFilled(true);
        set2.setFillAlpha(200);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);

        data.setDrawValues(false);
        data.setValueTextColor(getResources().getColor(R.color.black_russian));

        return data;
    }

}