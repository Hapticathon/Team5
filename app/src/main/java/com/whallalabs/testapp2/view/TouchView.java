package com.whallalabs.testapp2.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Jarek on 09.05.15.
 */
public class TouchView extends FrameLayout {

    enum Direction{LEFT, UP, RIGHT, DOWN};

    public static int WIDTH = 720;
    public static int HEIGTH = 1280;
    public static int ANIMATION_DURATION = 500;
    private int _count = 1;



    ImageView _mainImageView;
    ImageView _secondImageView;
    ImageView _currentMain;
    ImageView _currentSecond;

    String[][] _map;


    OnClickListener _onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(_count){
                case 1:{
                    swipeLeft();
                    break;
                }
                case 2:{
                    swipeRight();
                    break;
                }
            }
            _count++;
        }
    };

    public TouchView(Context context) {
        super(context);
        init();
    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init(){
        _mainImageView = new ImageView(getContext());
        _mainImageView.setLayoutParams(getLP());
        _mainImageView.setBackgroundColor(Color.RED);
        _mainImageView.setOnClickListener(_onClickListener);
        _mainImageView.setVisibility(VISIBLE);
        addView(_mainImageView);
        _currentMain = _mainImageView;

        _secondImageView = new ImageView(getContext());
        _secondImageView.setLayoutParams(getLP());
        _secondImageView.setBackgroundColor(Color.BLUE);
        _secondImageView.setOnClickListener(_onClickListener);
        _secondImageView.setVisibility(GONE);
        addView(_secondImageView);
        _currentSecond = _secondImageView;

    }


    private ViewGroup.LayoutParams getLP(){
        ViewGroup.LayoutParams result = new ViewGroup.LayoutParams(WIDTH, HEIGTH);
        return result;
    }


    public void setMap(String[][] map){
        _map = map;
    }

    public void swipe(Direction direction, int points){
        switch(direction){
            case LEFT:{
                _currentMain.setTranslationX(0);
                _currentMain.animate().translationXBy(points * -1).setDuration(ANIMATION_DURATION).start();
                _currentSecond.setTranslationX(points-1);
                setVisible(_currentSecond);
                _currentSecond.animate().translationXBy(points * -1).setDuration(ANIMATION_DURATION).start();

                _currentMain = _secondImageView;
                _currentSecond = _mainImageView;

                break;
            }
            case RIGHT:{
                _currentMain.setTranslationX(0);
                _currentMain.animate().translationXBy(points).setDuration(ANIMATION_DURATION).start();
                _currentSecond.setTranslationX(points * -1);
                setVisible(_currentSecond);
                _currentSecond.animate().translationXBy(points).setDuration(ANIMATION_DURATION).start();

                _currentMain = _secondImageView;
                _currentSecond = _mainImageView;

                break;
            }
        }
    }

    public void swipeLeft(){
        int distance = getDistance();
        swipe(Direction.LEFT,distance);
    }

    public void swipeRight(){
        int distance = getDistance();
        swipe(Direction.RIGHT,distance);
    }

    private int getDistance(){
        return _mainImageView.getMeasuredWidth();
    }

    private void setVisible(View view){
        if (view.getVisibility() !=  VISIBLE){
            view.setVisibility(VISIBLE);
        }
    }

}
