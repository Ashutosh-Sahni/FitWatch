package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView splash, subSplash;
    Button btn;
    ImageView splashImage;
    Animation atg, btgone, btgtwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splash = findViewById(R.id.tvSplash);
        subSplash = findViewById(R.id.tvSubSplash);
        btn = findViewById(R.id.btnGet);
        splashImage = findViewById(R.id.ivSplash);
        //initializing animation
        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        btgone = AnimationUtils.loadAnimation(this, R.anim.btgone);
        btgtwo = AnimationUtils.loadAnimation(this, R.anim.btgtwo);
        //passing animation
        splashImage.startAnimation(atg);
        splash.startAnimation(btgone);
        subSplash.startAnimation(btgone);
        btn.startAnimation(btgtwo);
        //initializing fonts
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/MLight.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MMedium.ttf");
        Typeface MRegular = Typeface.createFromAsset(getAssets(), "fonts/MRegular.ttf");
        //setting fonts
        splash.setTypeface(MRegular);
        subSplash.setTypeface(MLight);
        btn.setTypeface(MMedium);

        //pass an event on click (go to another activity)
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, StopwatchActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(a);
                finish();
            }
        });

    }
}