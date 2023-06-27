package com.example.linkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // wor to do after this delay time
                Intent gotoScanner = new Intent(Splash_Activity.this,scannerActivity.class);
                startActivity(gotoScanner);

            }
        },2000);
    }
}