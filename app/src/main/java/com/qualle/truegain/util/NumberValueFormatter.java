package com.qualle.truegain.util;

public class NumberValueFormatter {

    public static String toDisplayValue(float num) {
        if (num == Math.ceil(num)) {
            return String.valueOf((int) num);
        }
        return String.valueOf(num);
    }
}
