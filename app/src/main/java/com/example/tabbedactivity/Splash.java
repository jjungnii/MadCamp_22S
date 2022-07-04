package com.example.tabbedactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    Animation topAnim, bottomAnim, changeAnim;
    ImageView image1st, image2nd;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        changeAnim = AnimationUtils.loadAnimation(this, R.anim.change_animation);

        image1st = findViewById(R.id.intro_img_1st);
        image2nd = findViewById(R.id.intro_img_2nd);
        logo = findViewById(R.id.logo);

        image1st.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        image2nd.setAnimation(changeAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}