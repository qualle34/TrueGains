package com.qualle.shapeup.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatterUtil {

    private static final DateTimeFormatter simpleDateFormatter = DateTimeFormatter.ofPattern("d MMMM");

    public static String formatToSimpleDate(LocalDateTime dateTime) {
        return dateTime.format(simpleDateFormatter);
    }
}
