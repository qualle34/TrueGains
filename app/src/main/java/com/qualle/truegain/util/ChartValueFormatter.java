package com.qualle.truegain.util;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Locale;

public class ChartValueFormatter {

    public static ValueFormatter getDateValueFormatter() {
        return new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                int week = (int) value; // todo HOW TO GET CORRECT WEEK

//                int day = (int) value; // it is monday of week
//
//                LocalDate monday = LocalDate.ofEpochDay(day);
//
//                LocalDate sunday = monday
//                        .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

                LocalDate now = LocalDate.now();
                LocalDate monday;
                LocalDate sunday;

                if (value <= 0) {

                    int weeksOfYear = Calendar.getInstance().getActualMaximum(Calendar.WEEK_OF_YEAR);

                    monday = now.minusYears(1)
                            .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weeksOfYear + week)
                            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

                    sunday = now.minusYears(1)
                            .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weeksOfYear + week)
                            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

                } else {

                    monday = now.with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
                            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

                    sunday = now.with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
                            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                }

                if (monday.getMonth().equals(sunday.getMonth())) {
                    return monday.getDayOfMonth() + " - " + sunday.getDayOfMonth() + " " +
                            monday.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
                }

                return monday.getDayOfMonth() + " " + monday.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " - " +
                        sunday.getDayOfMonth() + " " + sunday.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            }
        };
    }

    public static ValueFormatter getDateDayValueFormatter() {
        return new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                int day = (int) value;
                Instant.ofEpochSecond(day * 86400L);

                LocalDate date = LocalDate.ofEpochDay(day);

                return date.getDayOfMonth() + " " + date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            }
        };
    }

    public static ValueFormatter getDefaultValueFormatter() {
        return new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                return value + "";
            }
        };
    }
}
