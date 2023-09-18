package com.qualle.shapeup.util;

import android.content.Context;

public class SizeConverter {

    public static int pxToDp(Context context, int px) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float)  density * px);
    }
}
