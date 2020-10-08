package com.example.stopwatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CountdownActivity extends AppCompatActivity {


    ImageView anchor;
    Button start, stop;
    Animation rotate;
    SeekBar seekBar;
    TextView clock;
    Boolean active=false;
    CountDownTimer mCountDownTimer;
    MediaPlayer bang;

    @SuppressLint("SetTextI18n")
    public void updateTime(int timeLeft)
    {
        int minutes = timeLeft /60;
        int seconds = timeLeft - minutes * 60;
        @SuppressLint("DefaultLocale") String secondsFormatted = String.format("%02d", seconds);
        clock.setText("0"+ minutes +":"+secondsFormatted);

    }

    public void controlTimer(View view)
    {
        if(!active) {
            seekBar.setVisibility(View.INVISIBLE);
            mCountDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTime((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    anchor.clearAnimation();
                    bang.start();
                }
            }.start();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        Button stopwatch = findViewById(R.id.stopwatch);
        final Button countdown = findViewById(R.id.countdown);
        countdown.setPaintFlags(countdown.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        start = findViewById(R.id.btnStart);
        anchor = findViewById(R.id.icanchor);
        stop = findViewById(R.id.btnStop);
        stop.setAlpha(0f);
        bang=MediaPlayer.create(getApplicationContext(), R.raw.bang);

        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);

        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MMedium.ttf");

        start.setTypeface(MMedium);
        stop.setTypeface(MMedium);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(300);
        seekBar.setProgress(150);
        seekBar.incrementProgressBy(15);

        clock = findViewById(R.id.clock);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int stepSize = 15;
                progress = ((int)Math.round(progress/stepSize))*stepSize;
                seekBar.setProgress(progress);
                updateTime(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anchor.startAnimation(rotate);
                stop.setTranslationY(60);
                stop.setVisibility(View.VISIBLE);
                stop.animate().alpha(1).setDuration(500).translationY(-40).start();
                start.animate().alpha(0).setDuration(500).translationY(40).start();
                start.setVisibility(View.INVISIBLE);
                controlTimer(v);
                active=true;
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                anchor.clearAnimation();
                stop.animate().alpha(0).setDuration(500).translationY(40).start();
                stop.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
                start.animate().alpha(1).setDuration(500).translationY(-40).start();
                active=false;
                mCountDownTimer.cancel();
                clock.setText("02:30");
                seekBar.setProgress(150);
                seekBar.setVisibility(View.VISIBLE);
            }
        });

        stopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(CountdownActivity.this, StopwatchActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(a);
                finish();
            }
        });
    }
}