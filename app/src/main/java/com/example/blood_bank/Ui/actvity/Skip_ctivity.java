package com.example.blood_bank.Ui.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.Button;

import com.example.blood_bank.Adaptar.SkipAdapter;
import com.example.blood_bank.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Skip_ctivity extends AppCompatActivity {

    @BindView(R.id.bloodbank_skip_button)
    Button bloodbankSkipButton;
    private static int currentPage = 0;
    private static int NUM_PAGES = 1;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skip_ctivity);
        ButterKnife.bind(this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        SkipAdapter Adapter = new SkipAdapter(getSupportFragmentManager());
        viewPager.setAdapter(Adapter);

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(viewpager);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {

                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                } else {
                    currentPage = currentPage + 1;
                }
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 1000);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }


            @OnClick(R.id.bloodbank_skip_button)
            public void onViewClicked() {
                Intent intent = new Intent(Skip_ctivity.this, User_Activity.class);
                startActivity(intent);
            }
        });

    }

}
