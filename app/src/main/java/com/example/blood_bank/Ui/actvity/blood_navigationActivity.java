package com.example.blood_bank.Ui.actvity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.blood_bank.helper.HelperMethod;
import com.example.blood_bank.R;
import com.example.blood_bank.Ui.fragment.AboutAppFragment;
import com.example.blood_bank.Ui.fragment.Artical_dontaion.ArticalsAndRequestDontaionFragment;
import com.example.blood_bank.Ui.fragment.Call_UsFragment;
import com.example.blood_bank.Ui.fragment.Edit_InformationFragment;
import com.example.blood_bank.Ui.fragment.Favorit_Fragment;
import com.example.blood_bank.Ui.fragment.RequestforDonation_Fragment;
import com.example.blood_bank.Ui.fragment.Siting_NavigationFragment;
import com.nex3z.notificationbadge.NotificationBadge;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class blood_navigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_notficatio_list)
    ImageView navNotficatioList;
    @BindView(R.id.FavListbadge)
    NotificationBadge FavListbadge;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bloodbank_content_navigation_fab)
    FloatingActionButton bloodbankContentNavigationFab;
    @BindView(R.id.bloodbank_navigation_content)
    ConstraintLayout bloodbankNavigationContent;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_navigation);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            ArticalsAndRequestDontaionFragment articalsAndRequestDontaion = new ArticalsAndRequestDontaionFragment();
            HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.bloodbank_navigation_content, articalsAndRequestDontaion);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.blood_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.my_information) {
            Edit_InformationFragment edit_information = new Edit_InformationFragment();
            HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.bloodbank_navigation_content, edit_information);

            // Handle the camera action
        } else if (id == R.id.siting_notfcation) {
            Siting_NavigationFragment edit_notifications = new Siting_NavigationFragment();
            HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.bloodbank_navigation_content, edit_notifications);

        } else if (id == R.id.fav) {

            Favorit_Fragment favorit = new Favorit_Fragment();
            HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.bloodbank_navigation_content, favorit);

        } else if (id == R.id.home) {

        } else if (id == R.id.used_information) {

        } else if (id == R.id.contenu) {
            Call_UsFragment callus = new Call_UsFragment();
            HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.bloodbank_navigation_content, callus);

        } else if (id == R.id.about_app) {
            AboutAppFragment about_app = new AboutAppFragment();
            HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.bloodbank_navigation_content, about_app);
        } else if (id == R.id.nav_star) {

        } else if (id == R.id.nav_logout) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.bloodbank_content_navigation_fab)

    public void onViewClicked() {
        RequestforDonation_Fragment donation_fragment=new RequestforDonation_Fragment();
        HelperMethod.replaceFragment(getSupportFragmentManager(),R.id.bloodbank_navigation_content,donation_fragment);
    }
}