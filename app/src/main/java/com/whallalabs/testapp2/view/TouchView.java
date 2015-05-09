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
    public static int ANIMATION_DURATION = 500;
    private int _count = 1;

    ImageView _mainImageView;
    ImageView _secondImageView;
    ImageView _currentMain;
    ImageView _currentSecond;

    String[][] _map;

    Animator.AnimatorListener _animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            _currentMain = _secondImageView;
            _currentSecond = _mainImageView;
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
            switch (_count) {
                case 1: {
                    swipeDown();
                    break;
                }
                case 2: {
                    swipeDown();
                    break;
                }
                case 3: {
                    swipeDown();
                    break;
                }
                case 4: {
                    swipeDown();
                    break;
                }
                case 5: {
                    swipeDown();
                    break;
                }
                case 6: {
                    swipeDown();
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

    private void init() {
        _mainImageView = new ImageView(getContext());
        _mainImageView.setLayoutParams(getLP());
        _mainImageView.setBackgroundColor(Color.RED);
        _mainImageView.setOnClickListener(_onClickListener);

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

    private ViewGroup.LayoutParams getLP() {
        ViewGroup.LayoutParams result = new ViewGroup.LayoutParams(WIDTH, HEIGTH);
        return result;
    }

    public void setMap(String[][] map) {
        _map = map;
    }

    public void swipe(Direction direction, int points) {
        switch (direction) {
            case LEFT: {
                _currentMain.setTranslationX(0);
                _currentMain.animate()
                        .setListener(_animatorListener)
                        .translationXBy(points * -1).setDuration(ANIMATION_DURATION).start();
                _currentSecond.setTranslationX(points - 1);
                setVisible(_currentSecond);
                _currentSecond.animate().translationXBy(points * -1).setDuration(ANIMATION_DURATION).start();


                break;
            }
            case RIGHT: {
                _currentMain.setTranslationX(0);
                _currentMain.animate()
                        .setListener(_animatorListener)
                        .translationXBy(points).setDuration(ANIMATION_DURATION).start();
                _currentSecond.setTranslationX(points * -1);
                setVisible(_currentSecond);
                _currentSecond.animate().translationXBy(points).setDuration(ANIMATION_DURATION).start();


                break;
            }

            case UP: {
//                _currentMain.setTranslationY(0);
//                _currentMain.animate()
//                        .setListener(_animatorListener)
//                        .translationYBy(points).setDuration(ANIMATION_DURATION).start();
//                _currentSecond.setTranslationY(points * -1);
//                setVisible(_currentSecond);
//                _currentSecond.animate().translationYBy(points).setDuration(ANIMATION_DURATION).start();
                Animation outAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_down);
                Animation inAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_down);

                _currentMain.setAnimation(outAnim);
                _currentSecond.setAnimation(inAnim);

                AnimationSet as = new AnimationSet(true);
                as.addAnimation(outAnim);
                as.addAnimation(inAnim);
                as.setInterpolator(new AccelerateInterpolator());
                as.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        _currentMain = _secondImageView;
                        _currentSecond = _mainImageView;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                as.startNow();


                break;
            }

            case DOWN: {
                _currentMain.setTranslationY(0);
                _currentMain.animate()
                        .setListener(_animatorListener)
                        .translationYBy(points * -1).setDuration(ANIMATION_DURATION).start();
                _currentSecond.setTranslationY(points);
                setVisible(_currentSecond);
                _currentSecond.animate().translationYBy(points * -1).setDuration(ANIMATION_DURATION).start();

                break;
            }
        }
    }

    public void swipeLeft() {
        int distance = getDistanceWidth();
        swipe(Direction.LEFT, distance);
    }

    public void swipeRight() {
        int distance = getDistanceWidth();
        swipe(Direction.RIGHT, distance);
    }

    public void swipeUp() {
        int distance = getDistanceHeigth();
        swipe(Direction.UP, distance);
    }

    public void swipeDown() {
        int distance = getDistanceHeigth();
        swipe(Direction.DOWN, distance);
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

}
