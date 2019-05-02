package com.example.blood_bank_mo.Adaptar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.blood_bank_mo.Ui.fragment.slider.fragment1;
import com.example.blood_bank_mo.Ui.fragment.slider.fragment2;


public class SkipAdapter extends FragmentPagerAdapter {
    Fragment[] fragments = {new fragment1(),new fragment2()};
    public SkipAdapter(FragmentManager fm) { super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
