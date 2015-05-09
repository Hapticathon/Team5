package com.whallalabs.testapp2.speechrecognition;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.whallalabs.testapp2.utils.ISwipeGesture;
import com.whallalabs.testapp2.utils.SwipeGestureDetector;

/**
 * Created by kamil on 09.05.15.
 */
public class SpeechRecognition {
    public final static int REQUEST_SPEECH_RECOGNITION = 55;
    private Activity activity;

    SwipeGestureDetector swipeGestureDetector;

    public SpeechRecognition(Activity activity, View doubleTapView) {
        this.activity = activity;
        initDoubleTabListener(doubleTapView);
        swipeGestureDetector = new SwipeGestureDetector((ISwipeGesture) activity);
    }

    public void startRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, activity.getClass()
                .getPackage().getName());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say institution name");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        activity.startActivityForResult(intent, REQUEST_SPEECH_RECOGNITION);
    }

    public void initDoubleTabListener(View view) {

        final GestureDetector gestureDetector = new GestureDetector(activity, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });

        gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent motionEvent) {
                startRecognition();
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                return false;
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getPointerCount() == 2) {
                    swipeGestureDetector.detecDirection(motionEvent);
//                }
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
    }
}
