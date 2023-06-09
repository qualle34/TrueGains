package com.qualle.shapeup.util;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;

public class ChartValueFormatter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");


    public static ValueFormatter getDateValueFormatter(){
        return new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                int week = (int) value;

//                LocalDate monday = LocalDate.now()
//                        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
//                        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

                LocalDate sunday = LocalDate.now()
                        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
                        .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

                return sunday.format(formatter);
            }
        };
    }
}
