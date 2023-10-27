package com.qualle.truegain.util;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class ChartValueFormatter {

    public static ValueFormatter getDateValueFormatter() {
        return new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                int week = (int) value; // todo HOW TO GET CORRECT WEEK

                LocalDate monday = LocalDate.now()
                        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
                        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

                LocalDate sunday = LocalDate.now()
                        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
                        .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

                if (monday.getMonth().equals(sunday.getMonth())) {
                    return monday.getDayOfMonth() + " - " + sunday.getDayOfMonth() + " " +
                            monday.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
                }

                return monday.getDayOfMonth() + " " + monday.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " - " +
                        sunday.getDayOfMonth() + " " + sunday.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            }
        };
    }
}
