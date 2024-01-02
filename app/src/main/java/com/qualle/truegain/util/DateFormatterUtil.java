package com.qualle.truegain.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
//            throw new RuntimeException("Unable to parse date: " + e.getMessage(), e);
            return "Loading error";
        }
    }

    public static String formatToSimpleDate(long epochDay) {
        try {
            LocalDate date = LocalDate.ofEpochDay(epochDay);
            return date.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + date.getDayOfMonth();
        } catch (RuntimeException e) {
//            throw new RuntimeException("Unable to parse date: " + e.getMessage(), e);
            return "Loading error";
        }
    }

    public static LocalDateTime fromTokenDate(long date) {

        try {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault());
        } catch (RuntimeException e) {
//            throw new RuntimeException("Unable to parse date: " + e.getMessage(), e);
            return LocalDateTime.MIN;
        }
    }

    public static LocalDate fromApiSimpleDate(String date) {

        try {
            return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (RuntimeException e) {
//            throw new RuntimeException("Unable to parse date: " + e.getMessage(), e);
            return LocalDate.MIN;
        }
    }


    public static LocalDateTime fromApiDate(String date) {

        try {
            return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (RuntimeException e) {
//            throw new RuntimeException("Unable to parse date: " + e.getMessage(), e);
            return LocalDateTime.MIN;
        }
    }

    public static String toApiDate(LocalDateTime date) {

        try {
            return date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (RuntimeException e) {
//            throw new RuntimeException("Unable to format date: " + e.getMessage(), e);
            return "Loading error";
        }
    }

    public static String dayNumberToDate(float value) {
        try {
            int day = (int) value;
            Instant.ofEpochSecond(day * 86400L);

            LocalDate date = LocalDate.ofEpochDay(day);

            return date.getDayOfMonth() + " " + date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        } catch (Exception e) {
            return "Loading error";
        }
    }


    public static float dateToDayNumber(LocalDateTime dateTime) {
        return (float) (dateTime.toInstant(ZoneOffset.UTC).getEpochSecond() / 86400);
    }
}