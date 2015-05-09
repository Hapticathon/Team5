package com.whallalabs.testapp2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Toast;

import com.whallalabs.testapp2.speechrecognition.PlacesRecognizer;
import com.whallalabs.testapp2.speechrecognition.SpeechRecognition;
import com.whallalabs.testapp2.utils.ISwipeGesture;
import com.whallalabs.testapp2.utils.MapFactory;
import com.whallalabs.testapp2.utils.SwipeGestureDetector;
import com.whallalabs.testapp2.utils.Utils;
import com.whallalabs.testapp2.view.TouchView;

import java.util.List;

import nxr.tpad.lib.TPad;
import nxr.tpad.lib.TPadImpl;
import nxr.tpad.lib.views.FrictionMapView;

import static com.whallalabs.testapp2.utils.SwipeGestureDetector.*;

public class MainActivity extends ActionBarActivity implements ISwipeGesture {

    private static final int TWO_FINGER = 2;
    private static final int SWIPE_THRESHOLD = 50;
    private TPad _tpad;
    private FrictionMapView _frictionMapView;
    private View _gestureView;
    private Context _activityContext;
    private GestureDetector gestureDetector;
    private SwipeGestureDetector _swipeGesture;
//    private SwipeGestureDetector.HSwipie _currentHEvent;
//    private SwipeGestureDetector.VSwipie _currentVEvent;
    private TouchView _touchView;
    private Integer[][] _maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _swipeGesture = new SwipeGestureDetector(this);
        gestureDetector = new GestureDetector(this, _swipeGesture);

        _frictionMapView = (FrictionMapView) findViewById(R.id.frictionmap);
        _touchView = (TouchView) findViewById(R.id.touchview);


        Bitmap bm = Utils.drawableToBitmap(getResources().getDrawable(R.drawable.map6));
        _frictionMapView.setDataBitmap(bm);

        checkVoiceRecognition();
        initTochEvents();
        initFrictionLayout();
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

    private void initTochEvents() {
        _gestureView = findViewById(R.id.gesture_view);

    }

    @Override
    public void actionUp(Swipe swipe) {
        Log.d(LOGTAG, swipe.name());
        switch (swipe){
            case UP:
                _touchView.swipeUp();
                break;
            case DOWN:
                _touchView.swipeDown();
                break;

            case LEFT:
                _touchView.swipeLeft();
                break;

            case RIGHT:
                _touchView.swipeRight();
                break;
        }
        Toast.makeText(this, swipe.name(), Toast.LENGTH_SHORT).show();
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

        map.getClass();
        _touchView.setMap(map);
        _touchView.init();

    }

    private void onPlaceRecognized(String place) {
        if(place.contains(PlacesRecognizer.mockPlaces[1])){
            //urzad miasta
           _maps =  MapFactory.getMap(MapFactory.MapType.HOUSE);

        }else if(place.contains(PlacesRecognizer.mockPlaces[0])){
            //lotnisko
            _maps = MapFactory.getMap(MapFactory.MapType.BEACON);

        }
    }
}
