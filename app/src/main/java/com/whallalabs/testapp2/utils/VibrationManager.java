package com.whallalabs.testapp2.utils;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by Maciej Markiewicz on 10.05.15.
 */
public class VibrationManager {
    public enum VibrationType {ACTION_SUCCESS, ACTION_FAIL, ACTION_WARNING}
    private Context _context;
    private Vibrator _vibrator;

    public VibrationManager(Context context){
        _context = context;
        Vibrator _vibrator = (Vibrator) _context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void vibrate(VibrationType type){
        switch (type){
            case ACTION_SUCCESS:
                _vibrator.vibrate(500);
                break;

            case ACTION_FAIL:
                long[] pattern = {500, 500, 500};
                _vibrator.vibrate(pattern, 3);
                break;

            case ACTION_WARNING:
                _vibrator.vibrate(1000);
                break;
        }

    }
}
