package com.example.blood_bank.Ui.actvity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.blood_bank.R;

public class SplashActivity extends AppCompatActivity {

    private long SPLASH_TIME_OUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                Intent intent =new Intent(SplashActivity.this,Skip_ctivity.class);
                startActivity(intent);
                // close this activity
                finish();
       }
    }, SPLASH_TIME_OUT);

}
}