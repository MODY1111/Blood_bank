package com.example.blood_bank.Ui.actvity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.blood_bank.helper.HelperMethod;
import com.example.blood_bank.R;
import com.example.blood_bank.Ui.fragment.LoginFragment;

public class User_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        if (savedInstanceState == null) {
            LoginFragment loginFragment = new LoginFragment();
            HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.bloodbank_User_fram, loginFragment);
        }
    }
}