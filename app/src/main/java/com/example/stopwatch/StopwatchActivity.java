package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

public class StopwatchActivity extends AppCompatActivity {

    ImageView anchor;
    Button start, stop;
    Animation rotate;
    Chronometer timer;
    MediaPlayer timeSound, bang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        Button stopwatch = findViewById(R.id.stopwatch);
        Button countdown = findViewById(R.id.countdown);
        stopwatch.setPaintFlags(stopwatch.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        timeSound = MediaPlayer.create(getApplicationContext(), R.raw.timer);
        bang = MediaPlayer.create(getApplicationContext(), R.raw.bang);

        start = findViewById(R.id.btnStart);
        anchor = findViewById(R.id.icanchor);
        stop = findViewById(R.id.btnStop);
        timer = findViewById(R.id.timer);
        stop.setAlpha(0f);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MMedium.ttf");
        start.setTypeface(MMedium);
        stop.setTypeface(MMedium);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anchor.startAnimation(rotate);
                stop.setTranslationY(60);
                stop.setVisibility(View.VISIBLE);
                stop.animate().alpha(1).setDuration(500).translationY(-40).start();
                start.animate().alpha(0).setDuration(500).translationY(40).start();
                start.setVisibility(View.INVISIBLE);
                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();
                timeSound.start();
                timeSound.setLooping(true);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anchor.clearAnimation();
                stop.animate().alpha(0).setDuration(500).translationY(40).start();
                stop.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
                start.animate().alpha(1).setDuration(500).translationY(-40).start();
                timer.stop();
                timeSound.pause();
                bang.start();
            }
        });

        countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(StopwatchActivity.this, CountdownActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(a);
                finish();
            }
        });
    }
}