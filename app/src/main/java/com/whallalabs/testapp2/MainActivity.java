package com.whallalabs.testapp2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.whallalabs.testapp2.speechrecognition.SpeechRecognition;
import com.whallalabs.testapp2.utils.Utils;

import java.util.List;

import nxr.tpad.lib.TPad;
import nxr.tpad.lib.TPadImpl;
import nxr.tpad.lib.views.FrictionMapView;

public class MainActivity extends ActionBarActivity {

    TPad _tpad;
    FrictionMapView _frictionMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _frictionMapView = (FrictionMapView) findViewById(R.id.frictionmap);
        Bitmap bm = Utils.drawableToBitmap(getResources().getDrawable(R.drawable.test1));
        _frictionMapView.setDataBitmap(bm);

        checkVoiceRecognition();
        initFrictionLayout();
        initSpeechRecognition();
    }

    private void initSpeechRecognition() {
        SpeechRecognition speechRecognition = new SpeechRecognition(this);
        speechRecognition.startRecognition();
    }

    private void initFrictionLayout() {
        _tpad = new TPadImpl(this);
        _frictionMapView.setTpad(_tpad);
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
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initTochEvents() {

    }
}
