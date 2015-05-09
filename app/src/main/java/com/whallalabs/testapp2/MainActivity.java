package com.whallalabs.testapp2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.whallalabs.testapp2.utils.ISwipeGesture;
import com.whallalabs.testapp2.utils.SwipeGestureDetector;
import com.whallalabs.testapp2.speechrecognition.PlacesRecognizer;
import com.whallalabs.testapp2.utils.MapFactory;
import com.whallalabs.testapp2.speechrecognition.SpeechRecognition;
import com.whallalabs.testapp2.utils.Utils;
import com.whallalabs.testapp2.view.TouchView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

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
    private SwipeGestureDetector.HSwipie _currentHEvent;
    private SwipeGestureDetector.VSwipie _currentVEvent;
    private TouchView _touchView;


//    private View.OnTouchListener _gestureTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent event) {
////            if(event.getPointerCount() == TWO_FINGER) {
////                _swipeGesture.detecDirection(event);
////            }
//
//            return false;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _swipeGesture = new SwipeGestureDetector(this);
        gestureDetector = new GestureDetector(this, _swipeGesture);

        _frictionMapView = (FrictionMapView) findViewById(R.id.frictionmap);
        _touchView = (TouchView) findViewById(R.id.touchview);

        Bitmap bm = Utils.drawableToBitmap(getResources().getDrawable(R.drawable.map5));
        _frictionMapView.setDataBitmap(bm);

        checkVoiceRecognition();
        initTochEvents();
        initFrictionLayout();
        SpeechRecognition speechRecognition = new SpeechRecognition(this, _frictionMapView);

//        _frictionMapView.setOnTouchListener(_gestureTouchListener);
    }


    public void checkVoiceRecognition() {
        // Check if voice recognition is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            Toast.makeText(this, "Voice recognizer not present",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void initTochEvents(){
        _gestureView =  findViewById(R.id.gesture_view);

    }

    @Override
    public void swipeUP() {
        if(_currentVEvent != SwipeGestureDetector.VSwipie.UP_SWIPE){
            up();
            _currentVEvent = SwipeGestureDetector.VSwipie.UP_SWIPE;
        }
    }

    @Override
    public void swipeDown() {
        if(_currentVEvent != SwipeGestureDetector.VSwipie.DOWN_SWIPE){
            down();
            _currentVEvent = SwipeGestureDetector.VSwipie.DOWN_SWIPE;
        }

    }

    @Override
    public void swipeLeft() {
        if(_currentHEvent != SwipeGestureDetector.HSwipie.LEFT_SWIPE) {
            left();
            _currentHEvent = SwipeGestureDetector.HSwipie.LEFT_SWIPE;
        }
    }

    @Override
    public void swipeRight() {
        if(_currentHEvent != SwipeGestureDetector.HSwipie.RIGHT_SWIPE) {
            right();
            _currentHEvent = SwipeGestureDetector.HSwipie.RIGHT_SWIPE;
        }
    }

    @Override
    public void actionUp() {
//        _tpad.tur
    }

    private void left() {
        _tpad.turnOff();
        Toast.makeText(this, "LEFT", Toast.LENGTH_SHORT).show();
        Log.d(SwipeGestureDetector.LOGTAG, "left");
    }

    private void right(){
        _tpad.turnOff();
        Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
        Log.d(SwipeGestureDetector.LOGTAG, "right");
    }


    public void up(){
        _tpad.turnOff();
        Toast.makeText(this, "UP", Toast.LENGTH_SHORT).show();
        Log.d(SwipeGestureDetector.LOGTAG, "up");
    }

    public void down(){
        _tpad.turnOff();
        Toast.makeText(this, "DOWN", Toast.LENGTH_SHORT).show();
        Log.d(SwipeGestureDetector.LOGTAG, "down");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SpeechRecognition.REQUEST_SPEECH_RECOGNITION) {
            if (data != null && data.getExtras() != null) {
                List<String> results = (List<String>) data.getExtras().get(RecognizerIntent.EXTRA_RESULTS);
                String recognizedPlace = PlacesRecognizer.getRecognizedPlace(results);
                if (recognizedPlace != null) {
                    onPlaceRecognized(recognizedPlace);
                    Log.i("Speech result", recognizedPlace);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initFrictionLayout() {
        _tpad = new TPadImpl(this);
        _frictionMapView.setTpad(_tpad);

        Integer[][] map = MapFactory.getMap(MapFactory.MapType.HOUSE);

        _touchView.setMap(map);
        _touchView.init();


    }


    private void onPlaceRecognized(String place) {

    }

}
