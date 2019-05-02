package com.example.blood_bank_mo.Ui.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.blood_bank_mo.Adaptar.SkipAdapter;
import com.example.blood_bank_mo.R;

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

    }

            @OnClick(R.id.bloodbank_skip_button)
            public void onViewClicked() {
                Intent intent = new Intent(Skip_ctivity.this, User_Activity.class);
                startActivity(intent);
            }


}
