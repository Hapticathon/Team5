package com.whallalabs.testapp2.speechrecognition;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

/**
 * Created by kamil on 09.05.15.
 */
public class SpeechRecognition {
    public final static int REQUEST_SPEECH_RECOGNITION = 55;
    private Activity activity;

    public SpeechRecognition(Activity activity) {
        this.activity = activity;
    }

    public void startRecognition() {
        SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, activity.getClass()
                .getPackage().getName());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say location");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        activity.startActivityForResult(intent, REQUEST_SPEECH_RECOGNITION);
    }
}
