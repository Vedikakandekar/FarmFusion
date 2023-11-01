package com.sem3.farmfusion;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView btmNav ;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btmNav = findViewById(R.id.btm_navig_view);
        frameLayout = findViewById(R.id.frame_layout);

        btmNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if(itemId == R.id.dashboard)
            {

                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout,homeFragment);
                transaction.commit();

            } else if (itemId == R.id.take_pic) {
                HealthifyPlant healthifyPlant = new HealthifyPlant();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout,healthifyPlant);
                transaction.commit();

            } else if (itemId == R.id.fertilizer_calculator) {
                FertilizerCalculator fertilizerCalculator = new FertilizerCalculator();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout,fertilizerCalculator);
                transaction.commit();


            } else if (itemId == R.id.community) {
                Community community = new Community();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout,community);
                transaction.commit();
            }

            return false;
        });


    }
}