package com.vacuity.myapplication.userinterface;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vacuity.myapplication.fragment.MapFragment;
import com.vacuity.myapplication.fragment.YelpListFragment;

/**
 * Created by Gary Straub on 8/10/2017.
 */

public class TabAdapter extends FragmentStatePagerAdapter {
    int numberOfTabs;
    public TabAdapter(FragmentManager fm, int tabNum){
        super(fm);
        this.numberOfTabs = tabNum;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
//            case 0:
//                TabFragmentOne tab1 = new TabFragmentOne();
//                return tab1;
            case 0:
                YelpListFragment tab1 = new YelpListFragment();
                return tab1;
//            case 1:
//                TabFragmentTwo tab2 = new TabFragmentTwo();
//                return tab2;
            case 1:
                MapFragment tab2 = new MapFragment();
                return tab2;
//            case 2:
//                YelpListFragment tab3 = new YelpListFragment();
//                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}

