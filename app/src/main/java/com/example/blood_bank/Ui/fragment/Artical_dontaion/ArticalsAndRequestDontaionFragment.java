package com.example.blood_bank.Ui.fragment.Artical_dontaion;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blood_bank.Adaptar.ViewPagerAdapter;
import com.example.blood_bank.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticalsAndRequestDontaionFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.dontaion_view)
    ViewPager dontaionView;
    @BindView(R.id.dontaion_Tab)
    TabLayout dontaionTab;

    public ArticalsAndRequestDontaionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_articals_and_request_dontaion, container, false);
        unbinder = ButterKnife.bind(this, view);
        Dontaion_RequestFragment dontaion_requestFragment = new Dontaion_RequestFragment();
        Dontaion_ArticalsFragment dontaion_articalsFragment = new Dontaion_ArticalsFragment();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addPager(dontaion_articalsFragment, "المقالات");
        viewPagerAdapter.addPager(dontaion_requestFragment, "طلبات التبرع");
        dontaionView.setAdapter(viewPagerAdapter);
        dontaionTab.setupWithViewPager(dontaionView);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
