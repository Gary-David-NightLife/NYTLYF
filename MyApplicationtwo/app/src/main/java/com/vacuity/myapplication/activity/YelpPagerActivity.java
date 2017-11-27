package com.vacuity.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.vacuity.myapplication.R;
import com.vacuity.myapplication.fragment.YelpFragment;
import com.vacuity.myapplication.models.model.YelpLab;
import com.vacuity.myapplication.models.Business;

import java.util.List;

/**
 * Created by Gary Straub on 11/13/2017.
 */

public class YelpPagerActivity extends AppCompatActivity {
    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Business> mCrimes;

    public static Intent newIntent(Context packageContext, String yelpID) {
        Intent intent = new Intent(packageContext, YelpPagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, yelpID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        String crimeId = (String) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);

        mCrimes = YelpLab.get(this).getBusiness();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Business crime = mCrimes.get(position);
                return YelpFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
