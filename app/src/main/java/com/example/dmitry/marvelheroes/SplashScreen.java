package com.example.dmitry.marvelheroes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Dmitry on 10.10.2015.
 */

public class SplashScreen extends AppCompatActivity {

    private static int SPLASHSCREEN_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent newIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(newIntent);
                finish();
            }
        }, SPLASHSCREEN_TIME);
    }

}
