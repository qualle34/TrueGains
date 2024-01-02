
package com.qualle.truegain.ui.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.qualle.truegain.R;

import java.text.DecimalFormat;


@SuppressLint("ViewConstructor")
public class XYMarkerView extends MarkerView {

    private final TextView tvContent;

    private final DecimalFormat format;

    public XYMarkerView(Context context) {
        super(context, R.layout.custom_marker_view);

        tvContent = findViewById(R.id.tvContent);
        format = new DecimalFormat("###.0");
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText(format.format(e.getY()) + " Kg");

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
