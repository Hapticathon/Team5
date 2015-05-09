package com.whallalabs.testapp2.utils;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Maciej Markiewicz on 09.05.15.
 */
public class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener{
    private static final int SWIPE_THRESHOLD = 50;
    public enum HSwipie {LEFT_SWIPE, RIGHT_SWIPE, NO_H_SWIPE};
    public enum VSwipie {UP_SWIPE, DOWN_SWIPE, NO_V_SWIPE};
    public final static String LOGTAG = "GESTUREDETECTOR";
    private final static int TWO_FINGERS = 2;
    private static final int UP =1;
    private static final int LEFT =2;
    private static final int DOWN =3;
    private static final int RIGHT =4;
    private boolean _isTwoFingers = false;
    private MotionEvent _eventDown;
    private MotionEvent _eventUp;
    private SwipeGestureDetector.HSwipie h_swipe;
    private SwipeGestureDetector.VSwipie v_swipe;
    private float baseX, baseY, previousX , previousY;
    private HSwipie currentHEvent;
    private HSwipie currentVEvent;
    

    private ISwipeGesture _swipeGesture;

    public SwipeGestureDetector(ISwipeGesture swipeGesture){
        super();
        _swipeGesture = swipeGesture;
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2,
                           float velocityX, float velocityY) {

        if(_isTwoFingers) {
            switch (getSlope(e1.getX(), e1.getY(), e2.getX(), e2.getY())) {
                case UP:
                    _swipeGesture.swipeUP();
                    break;
                case LEFT:
                    _swipeGesture.swipeLeft();
                    break;
                case DOWN:
                    _swipeGesture.swipeDown();
                    break;
                case RIGHT:
                    _swipeGesture.swipeRight();
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
       if(e2.getPointerCount() == TWO_FINGERS){
           _isTwoFingers = true;
       }
        return false;
    }

    public static int getSlope(float x1, float y1, float x2, float y2) {
        Double angle = Math.toDegrees(Math.atan2(y1 - y2, x2 - x1));
        if (angle > 45 && angle <= 135)
            // top
            return 1;
        if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)
            // left
            return 2;
        if (angle < -45 && angle>= -135)
            // down
            return 3;
        if (angle > -45 && angle <= 45)
            // right
            return 4;
        return 0;
    }

    public void addEventDown(MotionEvent event){
        _eventDown = event;
    }

    public void addEventUp(MotionEvent event){
        _eventUp = event;
    }

    public void calculateDirection(){
//        if(_eventDown.getPointerCount() == 2) {
            switch (getSlope( _eventUp.getX(0), _eventUp.getY(0),_eventDown.getX(0), _eventDown.getY(0)) ){
                case UP:
                    _swipeGesture.swipeUP();
                    break;
                case LEFT:
                    _swipeGesture.swipeLeft();
                    break;
                case DOWN:
                    _swipeGesture.swipeDown();
                    break;
                case RIGHT:
                    _swipeGesture.swipeRight();
                    break;
            }
//        }
    }

    @Override
    public boolean onDown(MotionEvent event) {

        return false;
    }

    public void setTwoFingers(){
        _isTwoFingers = true;
    }

    public void setOneFingers(){
        _isTwoFingers = false;
    }
    
    public void detecDirection(MotionEvent event){
        boolean result = false;

        float currentX      = 0.0f;
        float currentY      = 0.0f;

        float current_diffX = 0.0f;
        float current_diffY = 0.0f;

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                //Touch detected, record base X-Y coordinates
                baseX = event.getX();
                baseY = event.getY();

                //As the event is consumed, return true
                result = true;
                break;

            case MotionEvent.ACTION_MOVE:
                //Swipe started, get current X-Y coordinates and compute those with the base ones
                currentX = event.getX();
                currentY = event.getY();

                current_diffX = currentX - baseX;
                current_diffY = currentY - baseY;

                //...................................................Determine horizontal swipe direction
                if(h_swipe == HSwipie.LEFT_SWIPE)
                {
                    if( currentX > previousX )
                    {
                        //If here, then horizontal swipe has been reversed
                        h_swipe = HSwipie.RIGHT_SWIPE;

                        //Overwrite base coordinate
                        baseX = previousX;

                        //Recalculate Difference
                        current_diffX = currentX - baseX;
                    }
                    else
                    {
                        //NOP - Intentionally kept empty
                    }
                }
                else if(h_swipe == HSwipie.RIGHT_SWIPE)
                {
                    if( currentX < previousX )
                    {
                        //If here, then horizontal swipe has been reversed
                        h_swipe = HSwipie.LEFT_SWIPE;

                        //Overwrite base coordinate
                        baseX = previousX;

                        //Recalculate Difference
                        current_diffX = currentX - baseX;
                    }
                    else
                    {
                        //NOP - Intentionally kept empty
                    }
                }
                else
                {
                    //If here, then it's a fresh swipe event, so compare with base coordinates
                    if( currentX < baseX )
                    {
                        h_swipe = HSwipie.LEFT_SWIPE;
                    }
                    else if( currentX > baseX )
                    {
                        h_swipe = HSwipie.RIGHT_SWIPE;
                    }
                    else
                    {
                        //NOP - Intentionally kept empty
                    }
                }

                //...................................................Determine vertical swipe direction
                if(v_swipe == VSwipie.UP_SWIPE)
                {
                    if(currentY > previousY)
                    {
                        //If here, then vertical swipe has been reversed
                        v_swipe = VSwipie.DOWN_SWIPE;

                        //Overwrite base coordinate
                        baseY = previousY;

                        //Recalculate coordinate difference
                        current_diffY = currentY - baseY;
                    }
                    else
                    {
                        //NOP - Intentionally kept empty
                    }
                }
                else if(v_swipe == VSwipie.DOWN_SWIPE)
                {
                    if(currentY < previousY)
                    {
                        //If here, then vertical swipe has been reversed
                        v_swipe = VSwipie.UP_SWIPE;

                        //Overwrite base coordinate
                        baseY = previousY;

                        //Recalculate coordinate difference
                        current_diffY = currentY - baseY;
                    }
                    else
                    {
                        //NOP - Intentionally kept empty
                    }
                }
                else
                {
                    //If here, then it's a fresh swipe event, so compare with base coordinates
                    if( currentY < baseY )
                    {
                        v_swipe = VSwipie.UP_SWIPE;
                    }
                    else if( currentY > baseY )
                    {
                        v_swipe = VSwipie.DOWN_SWIPE;
                    }
                    else
                    {
                        //NOP - Intentionally kept empty
                    }
                }

                //Record current coordinates for future comparisons
                previousX = currentX;
                previousY = currentY;

                //................................Determine the prominent swipe (horizontal/vertical)
                if(Math.abs(current_diffX) > Math.abs(current_diffY))
                {
                    //It's a horizontal swipe
                    if (Math.abs(current_diffX) > SWIPE_THRESHOLD)
                    {
                        if (current_diffX > 0)
                        {
                                _swipeGesture.swipeRight();
                        }
                        else
                        {
                           _swipeGesture.swipeLeft();
                        }
                    }
                    else
                    {
                        //Not enough swipe movement, ignore.
                        //NOP - Intentionally kept empty
                    }
                }
                else
                {
                    //It's a vertical swipe
                    if (Math.abs(current_diffY) > SWIPE_THRESHOLD)
                    {
                        if (current_diffY > 0)
                        {
                            _swipeGesture.swipeDown();
                        }
                        else
                        {
                            _swipeGesture.swipeUP();
                        }
                    }
                    else
                    {
                        //Not enough swipe movement, ignore.
                        //NOP - Intentionally kept empty
                    }
                }

                //As the event is consumed, return true
                result = true;
                break;

            case MotionEvent.ACTION_UP:
                //Swipe ended, clear variables, if necessary
                h_swipe = HSwipie.NO_H_SWIPE;
                v_swipe = VSwipie.NO_V_SWIPE;

                baseX = 0.0f;
                baseY = 0.0f;

                previousX = 0.0f;
                previousY = 0.0f;

                _swipeGesture.actionUp();

                //As the event is consumed, return true
                result = true;
                break;

            default:

                //Do not consume event
                result = false;
                break;
        }
    }

}
