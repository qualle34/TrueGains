package com.qualle.truegain.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

public class AssetManagerUtil {

    public static Drawable getImage(Context context, String link) {
        try {
            AssetManager manager = context.getAssets();

            if (manager == null) {
                throw new RuntimeException();
            }

            InputStream ims = manager.open(link);
            return Drawable.createFromStream(ims, null);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
