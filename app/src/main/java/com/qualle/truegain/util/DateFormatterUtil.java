package com.qualle.truegain.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateFormatterUtil {

    private static final DateTimeFormatter simpleDateFormatter = DateTimeFormatter.ofPattern("d MMMM");

    public static String formatApiDate(String date) {
        return DateFormatterUtil.formatToSimpleDate(DateFormatterUtil.fromApiDate(date));
    }

    public static String formatToSimpleDate(LocalDateTime dateTime) {
        try {
            return dateTime.format(simpleDateFormatter);
        } catch (RuntimeException e) {
            throw new RuntimeException("Unable to parse date: " + e.getMessage(), e);
        }
    }

    public static String formatToSimpleDate(long epochDay) {
        try {
            LocalDate date = LocalDate.ofEpochDay(epochDay);
            return date.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + date.getDayOfMonth();
        } catch (RuntimeException e) {
            throw new RuntimeException("Unable to parse date: " + e.getMessage(), e);
        }
    }

    public static LocalDateTime fromApiDate(String date) {

        try {
            return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (RuntimeException e) {
            throw new RuntimeException("Unable to parse date: " + e.getMessage(), e);
        }
    }
}
