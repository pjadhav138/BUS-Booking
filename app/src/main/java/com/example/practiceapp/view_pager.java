package com.example.practiceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Switch;

import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

public class view_pager extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager pager;
    public  String TAG="TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        pager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab);

        // view pager integration
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(2);
        new CountDownTimer(6000,2000){

            @Override
            public void onTick(long millisUntilFinished) {
                Log.e(TAG, "onTick: "+pager.getCurrentItem() );
                switch (pager.getCurrentItem()){
                    case 0:
                        pager.setCurrentItem(1);
                        break;
                    case 1:
                        pager.setCurrentItem(2);
                        break;
                    case 2:
                        pager.setCurrentItem(0);
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new fragment1(position);

                case 1:
                    return new fragment2(position);

                case 2:
                    return new fragment3(position);

                default:
                    return null;

            }

        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}