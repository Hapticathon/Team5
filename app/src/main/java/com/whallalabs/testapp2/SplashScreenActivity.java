package com.whallalabs.testapp2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.whallalabs.testapp2.speechrecognition.PlacesRecognizer;
import com.whallalabs.testapp2.speechrecognition.SpeechRecognition;
import com.whallalabs.testapp2.utils.ISwipeGesture;
import com.whallalabs.testapp2.utils.MapFactory;
import com.whallalabs.testapp2.utils.SwipeGestureDetector;
import com.whallalabs.testapp2.utils.VibrationManager;

import java.util.List;


public class SplashScreenActivity extends ActionBarActivity implements ISwipeGesture {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        checkVoiceRecognition();
        startRecognition();
//        SpeechRecognition speechRecognition = new SpeechRecognition(this, findViewById(R.id.splash_root_layout));
//        speechRecognition.startRecognition();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SpeechRecognition.REQUEST_SPEECH_RECOGNITION) {
            if (data != null && data.getExtras() != null) {
                List<String> results = (List<String>) data.getExtras().get(RecognizerIntent.EXTRA_RESULTS);
                String recognizedPlace = PlacesRecognizer.getRecognizedPlace(results);
                if (recognizedPlace != null) {
                    onPlaceRecognized(recognizedPlace);
                    Log.i("Speech result", recognizedPlace);
                } else {
                    VibrationManager.vibrate(this, VibrationManager.VibrationType.ACTION_FAIL);
                    startRecognition();
                }
            } else {
                VibrationManager.vibrate(this, VibrationManager.VibrationType.ACTION_FAIL);
                startRecognition();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void startRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getClass()
                .getPackage().getName());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say institution name");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pl-PL");
        this.startActivityForResult(intent, SpeechRecognition.REQUEST_SPEECH_RECOGNITION);
    }

    private void onPlaceRecognized(String place) {
        Intent intent = new Intent(this, MainActivity.class);

        if (place.contains(PlacesRecognizer.mockPlaces[1])) {
            //urzad miasta
            intent.putExtra(MainActivity.ARG_PLACE_TYPE, MainActivity.PlaceType.CITY_HALL);

        } else if (place.contains(PlacesRecognizer.mockPlaces[0])) {
            //lotnisko
            intent.putExtra(MainActivity.ARG_PLACE_TYPE, MainActivity.PlaceType.AIRPORT);

        }
        finish();
        startActivity(intent);
    }

    @Override
    public void actionUp(SwipeGestureDetector.Swipe swipe) {

    }
}
