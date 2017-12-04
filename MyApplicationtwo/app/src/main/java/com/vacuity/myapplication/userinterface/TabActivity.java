package com.vacuity.myapplication.userinterface;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.vacuity.myapplication.R;
import com.vacuity.myapplication.models.Business;
import com.vacuity.myapplication.models.model.YelpLab;

import java.util.ArrayList;


/**
 * Created by Gary Straub on 8/10/2017.
 */

public class TabActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private Boolean searchBar;
    private Boolean searchClub;
    private Boolean searchRestaurant;
    private YelpLab sYelpLab;
    private ArrayList<Business> myRes;
    private DrawerLayout mDrawerLayout;

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

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


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
//        getMenuInflater().inflate(R.menu.drawer_menu, menu);
//        return false;
//    }
//    public boolean onOptionsItemSelected(MenuItem item){
//        int id = item.getItemId();
//        if(id == R.id.nav_refresh){
//            Toast.makeText(getApplicationContext(), "I exist", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_bar) {
            Toast.makeText(getApplicationContext(), "Bars", Toast.LENGTH_SHORT).show();
            Log.e("Drawer Menu", "Bars");
            // Handle the camera action
        } else if (id == R.id.nav_club) {
            Toast.makeText(getApplicationContext(), "Clubs", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_restaurant) {
            Toast.makeText(getApplicationContext(), "Restaurants", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_Favorites) {
            Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_refresh) {
            Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_SHORT).show();

        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
