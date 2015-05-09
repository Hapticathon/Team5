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


    private View.OnTouchListener _gestureTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
//            if(event.getPointerCount() == TWO_FINGER) {
                _swipeGesture.detecDirection(event);
//            }

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

        checkVoiceRecognition();
        initTochEvents();
        SpeechRecognition speechRecognition = new SpeechRecognition(this, _frictionMapView);
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

        String[][] map = MapFactory.getMap(MapFactory.MapType.BEACON);

        map.getClass();

    }


    private void onPlaceRecognized(String place) {

    }

}
