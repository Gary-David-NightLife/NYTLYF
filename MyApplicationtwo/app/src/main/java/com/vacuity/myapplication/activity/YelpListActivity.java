package com.vacuity.myapplication.activity;

import android.support.v4.app.Fragment;

import com.vacuity.myapplication.fragment.YelpListFragment;

/**
 * Created by Gary Straub on 11/13/2017.
 */

public class YelpListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new YelpListFragment();
    }
}
