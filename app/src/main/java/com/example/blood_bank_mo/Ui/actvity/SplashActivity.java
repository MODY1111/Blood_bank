package com.example.blood_bank_mo.Ui.actvity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.blood_bank_mo.R;
import com.example.blood_bank_mo.data.local.SharedPreferencesManger;

public class SplashActivity extends AppCompatActivity {

    private long SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i;
                if (SharedPreferencesManger.LoadBoolean(SplashActivity.this, SharedPreferencesManger.REMEMBER) && !SharedPreferencesManger.LoadData(SplashActivity.this, SharedPreferencesManger.USER_API_TOKEN).isEmpty()) {
                    i = new Intent(SplashActivity.this, blood_navigationActivity.class);
                    startActivity(i);
                }else {
                    Intent m;
                    m = new Intent(SplashActivity.this, Skip_ctivity.class);
                    startActivity(m);

                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}