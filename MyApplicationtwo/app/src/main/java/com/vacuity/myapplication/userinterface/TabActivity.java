package com.vacuity.myapplication.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.vacuity.myapplication.R;
import com.vacuity.myapplication.activity.YelpPagerActivity;
import com.vacuity.myapplication.models.Business;
import com.vacuity.myapplication.models.model.YelpLab;

import java.util.ArrayList;


/**
 * Created by Gary Straub on 8/10/2017.
 */

public class TabActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Boolean searchBar;
    private Boolean searchClub;
    private Boolean searchRestaurant;
    private YelpLab sYelpLab;
    private ArrayList<Business> myRes;
    private DrawerLayout mDrawerLayout;
    private ToggleButton mToggleButtonBar;
    private ToggleButton mToggleButtonClub;
    private Button mToggleButtonRestaurant;
    ListView mListView;
    TextView mTextView;


    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        sYelpLab = YelpLab.get(getApplicationContext());

        setContentView(R.layout.tab_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.addTab(tabLayout.newTab().setText("List"));
//        tabLayout.addTab(tabLayout.newTab().setText("History"));
        tabLayout.addTab(tabLayout.newTab().setText("Calendar"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.draw_nav);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();



        mListView = (ListView)findViewById(R.id.con_list_view);

//        Menu menu = navigationView.getMenu();
//        MenuItem myItemOne = menu.findItem(R.id.menu_cat_a);
//        View actionView = MenuItemCompat.getActionView(myItemOne);
//        mToggleButtonBar = (ToggleButton) actionView.findViewById(R.id.nav_bar);
//        mToggleButtonBar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                YelpLab tYelpLab = YelpLab.get(getApplicationContext());
//
//                if (isChecked) {
//                    tYelpLab.setBar(Boolean.TRUE);
//                    Toast.makeText(getApplicationContext(), "Barssss", Toast.LENGTH_SHORT).show();
//                    Log.e("Drawer Menu", "Bars");
//                } else {
//                    tYelpLab.setBar(Boolean.FALSE);
//                    Log.e("Drawer Menu", "Bars");
//                    Toast.makeText(getApplicationContext(), "Barsssss", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
////        mToggleButtonClub = (ToggleButton) actionView.findViewById(R.id.nav_club);
////        mToggleButtonClub.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                if (isChecked) {
////                    Toast.makeText(getApplicationContext(), "Clubs", Toast.LENGTH_SHORT).show();
////                    Log.e("Drawer Menu", "Bars");
////                } else {
////                    Log.e("Drawer Menu", "Bars");
////                    Toast.makeText(getApplicationContext(), "Clubs", Toast.LENGTH_SHORT).show();
////                }
////            }
////        });
        MenuItem myItemTwo = menu.findItem(R.id.menu_cat_b);
        View actionViewTwo = MenuItemCompat.getActionView(myItemTwo);
//        TextView tV = (TextView) actionViewTwo.findViewById(R.id.nav_restaurant);
//        View v = actionViewTwo.findViewById(R.id.nav_Favorites);
//        this.registerForContextMenu(navigationView);

        mToggleButtonRestaurant = (Button) actionViewTwo.findViewById(R.id.nav_restaurant);
        mToggleButtonRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(v);
                openContextMenu(v);
            }
        });

        MenuItem myItemOne = menu.findItem(R.id.menu_cat_a);
        View actionView = MenuItemCompat.getActionView(myItemOne);
        mTextView = (TextView) actionView.findViewById(R.id.nav_bar);
        mTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                registerForContextMenu(v);
                openContextMenu(v);
            }
        });




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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.e("Hello", "World");
//        if(v.getId() == R.id.nav_refresh){
//            this.getMenuInflater().inflate(R.menu.context_menu, menu);
//        }

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        String[] t = {"item1", "item2", "item3", "item4", "item5"};
        for(int i = 0; i< 5; i++){
            YelpLab tLab = YelpLab.get(this);
            ArrayList<Business> tList = tLab.getBusiness();
            Double d = tList.get(i).getDistance()*0.000621371;
            String tmp = String.format("%.2f", d);
            String m = tList.get(i).getName() + " (" + tmp + "m)";

            menu.add(0,v.getId(),0, m);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        YelpLab tLab = YelpLab.get(this);
        ArrayList<Business> tList = tLab.getBusiness();
        for(int i =0; i< tList.size(); i++){
            Double d = tList.get(i).getDistance()*0.000621371;
            String tmp = String.format("%.2f", d);
            String m = tList.get(i).getName() + " (" + tmp + "m)";
            if(item.getTitle().toString().equals(m)){
                Intent intent = YelpPagerActivity.newIntent(this, tList.get(i).getId());
                startActivity(intent);
            }
        }

        return super.onContextItemSelected(item);


    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.nav_refresh){
            Toast.makeText(getApplicationContext(), "I exist", Toast.LENGTH_SHORT).show();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        YelpLab tYelpLab = YelpLab.get(getApplicationContext());
        int id = item.getItemId();

        if (id == R.id.nav_refresh) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else if (id == R.id.nav_restaurant) {
//            Toast.makeText(getApplicationContext(), "Clubs", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_restaurant) {
//            Toast.makeText(getApplicationContext(), "Restaurants", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_Favorites) {
//            Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_SHORT).show();
//            Log.e("Drawer Menu", "Bars");
        } else if (id == R.id.venue) {
            if(item.isChecked()){
                item.setChecked(false);

            }else{
                item.setChecked(true);
            }
//            Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_SHORT).show();
//            Log.e("Drawer Menu", "Bars");
            return false;
        } else if (id == R.id.n_bars){
            if(item.isChecked()){
                item.setChecked(false);
                tYelpLab.setBar(Boolean.FALSE);
            }else{
                item.setChecked(true);
                tYelpLab.setBar(Boolean.TRUE);
            }
            return false;
        } else if (id == R.id.n_clubs){
            if(item.isChecked()){
                item.setChecked(false);
                tYelpLab.setClub(Boolean.FALSE);
            }else{
                item.setChecked(true);
                tYelpLab.setClub(Boolean.TRUE);
            }
            return false;
        } else if (id == R.id.n_rest){
            if(item.isChecked()){
                item.setChecked(false);
                tYelpLab.setClub(Boolean.FALSE);
            }else{
                item.setChecked(true);
                tYelpLab.setClub(Boolean.TRUE);
            }
            return false;
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

}
