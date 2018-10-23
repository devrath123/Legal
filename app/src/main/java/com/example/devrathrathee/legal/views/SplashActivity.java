package com.example.devrathrathee.legal.views;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!SharedPreferenceManager.getInstance(SplashActivity.this).getString(Constants.USER_ID).equals("")) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 2500);
    }
}
