package com.tk.youfan.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.tk.youfan.R;

public class SpeachActivity extends Activity {
    ImageView img_circle;
    private ImageView img_circle2;
    private ImageView img_circle3;
    ImageView img_mic;
    private ImageView img_circle4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_speach);
        initView();
        initListener();
    }

    private void initListener() {
        img_mic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        img_circle.setVisibility(View.VISIBLE);
                        img_circle2.setVisibility(View.VISIBLE);
                        img_circle3.setVisibility(View.VISIBLE);
                        img_circle4.setVisibility(View.VISIBLE);
                        initAnim();
                        break;
                    case MotionEvent.ACTION_UP:
                        clearAnim();
                        img_circle.setVisibility(View.GONE);
                        img_circle2.setVisibility(View.GONE);
                        img_circle3.setVisibility(View.GONE);
                        img_circle4.setVisibility(View.GONE);
                        break;
                }
                return true;
            }
        });
    }

    private void initView() {
        img_mic = (ImageView) findViewById(R.id.img_mic);
        img_circle = (ImageView) findViewById(R.id.img_circle);
        img_circle2 = (ImageView) findViewById(R.id.img_circle2);
        img_circle3 = (ImageView) findViewById(R.id.img_circle3);
        img_circle4 = (ImageView) findViewById(R.id.img_circle4);

    }

    private void initAnim() {
        ScaleAnimation scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.speach_mic_anim);
        ScaleAnimation scaleAnimation2 = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.speach_mic_anim2);
        ScaleAnimation scaleAnimation3 = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.speach_mic_anim3);
        Animation scaleAnimation4 = AnimationUtils.loadAnimation(this, R.anim.speach_mic_anim4);
        img_circle.startAnimation(scaleAnimation);
        img_circle2.startAnimation(scaleAnimation2);
        img_circle3.startAnimation(scaleAnimation3);
        img_circle4.startAnimation(scaleAnimation4);
    }

    private void clearAnim() {
        img_circle.clearAnimation();
        img_circle2.clearAnimation();
        img_circle3.clearAnimation();
        img_circle4.clearAnimation();

    }
}
