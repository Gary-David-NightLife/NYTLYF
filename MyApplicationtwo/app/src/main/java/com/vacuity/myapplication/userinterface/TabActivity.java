package com.vacuity.myapplication.userinterface;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vacuity.myapplication.R;
import com.vacuity.myapplication.models.Business;
import com.vacuity.myapplication.models.model.YelpLab;

import java.util.ArrayList;


/**
 * Created by Gary Straub on 8/10/2017.
 */

public class TabActivity extends AppCompatActivity {
    private Boolean searchBar;
    private Boolean searchClub;
    private Boolean searchRestaurant;
    private YelpLab sYelpLab;
    private ArrayList<Business> myRes;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        sYelpLab = YelpLab.get(getApplicationContext());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.addTab(tabLayout.newTab().setText("List"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
        tabLayout.addTab(tabLayout.newTab().setText("Calendar"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TabAdapter adapter = new TabAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount()) {
        };
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

        @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        @Override
            public void onTabReselected(TabLayout.Tab tab) {

        }

    });
    }




//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return false;
//    }
//    public boolean onOptionsItemSelected(MenuItem item){
//        int id = item.getItemId();
//        if(id == R.id.action_search){
//            Toast.makeText(getApplicationContext(), "I exist", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
