package com.qualle.truegain.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateFormatterUtil {

    private static final DateTimeFormatter simpleDateFormatter = DateTimeFormatter.ofPattern("d MMMM");

    public static String formatToSimpleDate(LocalDateTime dateTime) {
        return dateTime.format(simpleDateFormatter);
    }

    public static String formatToSimpleDate(long epochDay) {
        LocalDate date = LocalDate.ofEpochDay(epochDay);
        return date.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + date.getDayOfMonth();
    }
}
