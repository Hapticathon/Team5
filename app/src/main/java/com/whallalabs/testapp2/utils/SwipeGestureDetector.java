package com.whallalabs.testapp2.utils;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Maciej Markiewicz on 09.05.15.
 */
public class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
    public enum Swipe {UP, DOWN, RIGHT, LEFT}

    public final static String LOGTAG = "GESTUREDETECTOR";
    private float baseX, baseY;
    private boolean doubleFingers = false;
    private ISwipeGesture _swipeGesture;

    public SwipeGestureDetector(ISwipeGesture swipeGesture) {
        super();
        _swipeGesture = swipeGesture;
    }

    public void detecDirection(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            doubleFingers = true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                baseX = event.getX();
                baseY = event.getY();
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (doubleFingers) {
                    doubleFingers = false;
                    Swipe swipe = determineDirection(event);
                    _swipeGesture.actionUp(swipe);
                }
                break;
            }
        }
    }

    private Swipe determineDirection(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();

        float current_diffX = currentX - baseX;
        float current_diffY = currentY - baseY;
        Swipe swipe;

        if (Math.abs(current_diffX) > Math.abs(current_diffY)) {
            if (current_diffX > 0) {
                swipe = Swipe.LEFT;
            } else {
                swipe = Swipe.RIGHT;
            }
        } else {
            if (current_diffY > 0) {
                swipe = Swipe.UP;
            } else {
                swipe = Swipe.DOWN;
            }
        }
        return swipe;
    }
}
