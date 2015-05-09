package com.whallalabs.testapp2.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by MnM on 09.05.15.
 */
public class Utils {

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        return bitmap;
    }
}
