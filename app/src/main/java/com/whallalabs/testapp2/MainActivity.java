package com.whallalabs.testapp2;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.whallalabs.testapp2.utils.ISwipeGesture;
import com.whallalabs.testapp2.utils.SwipeGestureDetector;
import com.whallalabs.testapp2.utils.Utils;

import nxr.tpad.lib.TPad;
import nxr.tpad.lib.TPadImpl;
import nxr.tpad.lib.views.FrictionMapView;

public class MainActivity extends ActionBarActivity implements ISwipeGesture{

    private static final int TWO_FINGER = 2;
    private static final int SWIPE_THRESHOLD = 50;
    private TPad _tpad;
    private FrictionMapView _frictionMapView;
    private View _gestureView;
    private Context _activityContext;
    private GestureDetector gestureDetector;
    private SwipeGestureDetector _swipeGesture;


    private View.OnTouchListener _gestureTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if(event.getPointerCount() == TWO_FINGER) {
                _swipeGesture.detecDirection(event);
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _swipeGesture = new SwipeGestureDetector(this);
        gestureDetector = new GestureDetector(this, _swipeGesture);

        _frictionMapView = (FrictionMapView) findViewById(R.id.frictionmap);
        _frictionMapView.setOnTouchListener(_gestureTouchListener);

        Bitmap bm = Utils.drawableToBitmap(getResources().getDrawable(R.drawable.test1));
                _frictionMapView.setDataBitmap(bm);

        _activityContext = this;
        initFrictionLayout();
        initTochEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initFrictionLayout(){
        _tpad = new TPadImpl(this);
        _frictionMapView.setTpad(_tpad);

    }

    private void initTochEvents(){
        _gestureView =  findViewById(R.id.gesture_view);

    }

    @Override
    public void swipeUP() {
        _tpad.turnOff();
        Log.d(SwipeGestureDetector.LOGTAG, "up");
    }

    @Override
    public void swipeDown() {
        _tpad.turnOff();
        Log.d(SwipeGestureDetector.LOGTAG, "down");
    }

    @Override
    public void swipeLeft() {
        _tpad.turnOff();
        Log.d(SwipeGestureDetector.LOGTAG, "left");
    }

    @Override
    public void swipeRight() {
        _tpad.turnOff();
        Log.d(SwipeGestureDetector.LOGTAG, "right");
    }
}
