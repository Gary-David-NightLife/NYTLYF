package com.vacuity.myapplication.userinterface;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vacuity.myapplication.R;


/**
 * Created by Gary Straub on 8/10/2017.
 */

public class TabActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mtoolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("List"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
//        tabLayout.addTab(tabLayout.newTab().setText("NFC Tags"));
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
//        return true;
//    }
//    public boolean onOptionsItemSelected(MenuItem item){
//        int id = item.getItemId();
//        if(id == R.id.action_search){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
