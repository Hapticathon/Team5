package com.whallalabs.testapp2.utils;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by Maciej Markiewicz on 10.05.15.
 */
public class VibrationManager {
    public enum VibrationType {ACTION_SUCCESS, ACTION_FAIL, ACTION_WARNING}

    public static void vibrate(Context _context, VibrationType type){
        Vibrator _vibrator = (Vibrator) _context.getSystemService(Context.VIBRATOR_SERVICE);
        switch (type){
            case ACTION_SUCCESS:
                _vibrator.vibrate(500);
                break;

            case ACTION_FAIL:
                long[] pattern = { 0, 500, 100, 500, 100, 500};
                _vibrator.vibrate(pattern, -1);
                break;

            case ACTION_WARNING:
                _vibrator.vibrate(1500);
                break;
        }

    }
}
