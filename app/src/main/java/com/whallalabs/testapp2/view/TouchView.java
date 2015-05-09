package com.whallalabs.testapp2.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.whallalabs.testapp2.R;

/**
 * Created by Jarek on 09.05.15.
 */
public class TouchView extends FrameLayout {

    enum Direction {LEFT, UP, RIGHT, DOWN}

    ;

    public static int WIDTH = 720;
    public static int HEIGTH = 1280;
    public static int ANIMATION_DURATION = 3000;
    private int _count = 1;
    private int _cuurentX = 1;
    private int _cuurentY = 1;

    ImageView _mainImageView;
    ImageView _secondImageView;
    ImageView _currentMain;
    ImageView _currentSecond;

    Integer[][] _map;

    Animator.AnimatorListener _animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };

    OnClickListener _onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
//            switch (_count) {
//                case 1: {
//                    swipeDown();
//                    break;
//                }
//                case 2: {
//                    swipeUp();
//                    break;
//                }
//                case 3: {
//                    swipeLeft();
//                    break;
//                }
//                case 4: {
//                    swipeLeft();
//                    break;
//                }
//                case 5: {
//                    swipeRight();
//                    break;
//                }
//                case 6: {
//                    swipeUp();
//                    break;
//                }
//            }
//            _count++;
        }
    };

    public TouchView(Context context) {
        super(context);
//        init();
    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init();
    }

    public TouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init();
    }

    public void init() {
        _mainImageView = new ImageView(getContext());
        _mainImageView.setLayoutParams(getLP());
        _mainImageView.setImageResource(_map[_cuurentX][_cuurentY]);
        _mainImageView.setOnClickListener(_onClickListener);
        _mainImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        addView(_mainImageView);
        _currentMain = _mainImageView;

        _secondImageView = new ImageView(getContext());
        _secondImageView.setLayoutParams(getLP());
        _secondImageView.setBackgroundColor(Color.BLUE);
        _secondImageView.setOnClickListener(_onClickListener);
        _secondImageView.setVisibility(GONE);
        _secondImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(_secondImageView);
        _currentSecond = _secondImageView;

    }

    private ViewGroup.LayoutParams getLP() {
        ViewGroup.LayoutParams result = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return result;
    }

    public void setMap(Integer[][] map) {
        _map = map;
    }

    public void swipe(Direction direction, int points) {
        _currentMain.setTranslationX(0);
        _currentMain.setTranslationY(0);
        switch (direction) {
            case LEFT: {
                _currentMain.animate()
                        .setListener(_animatorListener)
                        .translationXBy(points).setDuration(ANIMATION_DURATION).start();
                _currentSecond.setTranslationX(points * -1);
                _currentSecond.setTranslationY(0);
                setVisible(_currentSecond);
                _currentSecond.animate().translationXBy(points).setDuration(ANIMATION_DURATION).start();
                switchCurrent();


//                _currentMain.animate()
//                        .setListener(_animatorListener)
//                        .translationXBy(points * -1).setDuration(ANIMATION_DURATION).start();
//                _currentSecond.setTranslationX(points - 1);
//                setVisible(_currentSecond);
//                _currentSecond.animate().translationXBy(points * -1).setDuration(ANIMATION_DURATION).start();
//                switchCurrent();

                break;
            }
            case RIGHT: {
                _currentMain.animate()
                        .setListener(_animatorListener)
                        .translationXBy(points * -1).setDuration(ANIMATION_DURATION).start();
                _currentSecond.setTranslationX(points);
                _currentSecond.setTranslationY(0);
                setVisible(_currentSecond);
                _currentSecond.animate().translationXBy(points * -1).setDuration(ANIMATION_DURATION).start();
                switchCurrent();

                break;
            }

            case UP: {
                _currentMain.animate()
                        .setListener(_animatorListener)
                        .translationYBy(points).setDuration(ANIMATION_DURATION).start();
                _currentSecond.setTranslationY(points * -1);
                _currentSecond.setTranslationX(0);
                setVisible(_currentSecond);
                _currentSecond.animate().translationYBy(points).setDuration(ANIMATION_DURATION).start();
                switchCurrent();


                break;
            }

            case DOWN: {
                _currentMain.animate()
                        .setListener(_animatorListener)
                        .translationY(points * -1).setDuration(ANIMATION_DURATION).start();
                _currentSecond.setTranslationY(points);
                _currentSecond.setTranslationX(0);
                setVisible(_currentSecond);
                _currentSecond.animate().translationY(0).setDuration(ANIMATION_DURATION).start();
                switchCurrent();

                break;
            }
        }
    }

    public void swipeLeft() {
        Integer res = null;
        try {
            res = _map[_cuurentX - 1][_cuurentY];
        } catch (Exception e) {

        }


        if (res != null) {
            _secondImageView.setImageResource(res);
            int distance = getDistanceWidth();
            swipe(Direction.LEFT, distance);
            _cuurentX--;
        }

    }

    public void swipeRight() {
        Integer res = null;
        try {
            res = _map[_cuurentX + 1][_cuurentY];
        } catch (Exception e) {

        }

        if (res != null) {
            _secondImageView.setImageResource(res);
            int distance = getDistanceWidth();
            swipe(Direction.RIGHT, distance);
            _cuurentX++;
        }
    }

    public void swipeUp() {
        Integer res = null;
        try {
            res = _map[_cuurentX][_cuurentY - 1];
        } catch (Exception e) {

        }

        if (res != null) {
            _secondImageView.setImageResource(res);
            int distance = getDistanceHeigth();
            swipe(Direction.UP, distance);
            _cuurentY--;
        }
    }

    public void swipeDown() {
        Integer res = null;
        try {

            res = _map[_cuurentX][_cuurentY + 1];
        } catch (Exception e) {

        }
        if (res != null) {
            _secondImageView.setImageResource(res);
            int distance = getDistanceHeigth();
            swipe(Direction.DOWN, distance);
            _cuurentY++;
        }
    }

    private int getDistanceWidth() {
        return _mainImageView.getMeasuredWidth();
    }

    private int getDistanceHeigth() {
        return _mainImageView.getMeasuredHeight();
    }

    private void setVisible(View view) {
        if (view.getVisibility() != VISIBLE) {
            view.setVisibility(VISIBLE);
        }
    }

    private void switchCurrent() {

        ImageView tmp = _currentMain;
        _currentMain = _currentSecond;
        _currentSecond = tmp;
    }

}
