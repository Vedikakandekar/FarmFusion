package com.sem3.farmfusion;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView btmNav ;
    FrameLayout frameLayout;
    TextView pageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btmNav = findViewById(R.id.btm_navig_view);
        frameLayout = findViewById(R.id.frame_layout);
        pageName = findViewById(R.id.TV_page_name);

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.frame_layout,homeFragment);
        transaction1.commit();
        pageName.setText("Dashboard");

        btmNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if(itemId == R.id.dashboard)
            {


                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout,homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                pageName.setText("Dashboard");

            } else if (itemId == R.id.take_pic) {
                HealthifyPlant healthifyPlant = new HealthifyPlant();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout,healthifyPlant);
                transaction.addToBackStack(null);
                transaction.commit();
                pageName.setText("Healthify Plant");

            } else if (itemId == R.id.fertilizer_calculator) {
                FertilizerCalculator fertilizerCalculator = new FertilizerCalculator();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout,fertilizerCalculator);
                transaction.addToBackStack(null);
                transaction.commit();
                pageName.setText("Fertilizer Calculator");

            } else if (itemId == R.id.community) {
                Community community = new Community();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout,community);
                transaction.addToBackStack(null);
                transaction.commit();
                pageName.setText("Community");
            }

            return false;
        });


    }
}