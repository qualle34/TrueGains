package com.qualle.truegain.util;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class PieChartFormatter extends ValueFormatter {


    public DecimalFormat mFormat;
    private PieChart pieChart;

    public PieChartFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    public PieChartFormatter(PieChart pieChart) {
        this();
        this.pieChart = pieChart;
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + " %";
    }

    @Override
    public String getPieLabel(float value, PieEntry pieEntry) {

        return getFormattedValue(value);

    }


}
