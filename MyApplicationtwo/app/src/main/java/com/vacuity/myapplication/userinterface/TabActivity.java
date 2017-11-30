package com.vacuity.myapplication.userinterface;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.vacuity.myapplication.R;
import com.vacuity.myapplication.connection.YelpFusionApi;
import com.vacuity.myapplication.connection.YelpFusionApiFactory;
import com.vacuity.myapplication.models.Business;
import com.vacuity.myapplication.models.SearchResponse;
import com.vacuity.myapplication.models.model.YelpLab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static junit.framework.Assert.assertNotNull;


/**
 * Created by Gary Straub on 8/10/2017.
 */

public class TabActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private Boolean searchBar;
    private Boolean searchClub;
    private Boolean searchRestaurant;
    private YelpLab sYelpLab;
    private ArrayList<Business> myRes;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mtoolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("List"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
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




    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);


        searchBar = menu.findItem(R.id.menu_bar).isChecked();
        searchClub = menu.findItem(R.id.menu_club).isChecked();
        searchRestaurant = menu.findItem(R.id.menu_restaurant).isChecked();

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Search");
        searchView.setSubmitButtonEnabled(true);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "I exist", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getApplicationContext(), "Text Submit", Toast.LENGTH_SHORT).show();
        Log.e("Menu", "Query Search");
        Map<String, String> paramters = new HashMap<>();
        String [] queryTerms = query.split(" ");
        for(String ss : queryTerms){
            paramters.put("term", ss);
        }
        if(searchBar){
            paramters.put("categories", "bars");
        }
        if(searchClub){
            paramters.put("categories", "danceclubs");
            paramters.put("categories", "nightlife");
        }
        if(searchRestaurant){
            paramters.put("categories", "Restaurant");
        }
        paramters.put("location", "94132");

        try{
            //MapFragment.SearchTask searchTask = new MapFragment.SearchTask();
            YelpSearchTask searchTask = new YelpSearchTask();
            searchTask.execute(paramters);
        }catch (IOException e){
            e.printStackTrace();
        }


        //YelpLab sYelpLab = YelpLab.get(getActivity());
        //myMap.put("term", "Restaurant");

//        myMap.put("location", "sf");
//
//        //ArrayList<Business> myYelps = sYelpLab.getBusiness();
        Log.e("Menu", "Query Search");
//        sYelpLab.getBusiness();
//        updateUI();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(getApplicationContext(), "Text Change", Toast.LENGTH_SHORT).show();
        return false;
    }
    private class YelpSearchTask extends AsyncTask<Map<String, String>, Integer, ArrayList<Business>> {
        private ArrayList<Business> mBusiness;
        YelpFusionApiFactory yelpFusionApiFactory;
        YelpFusionApi yelpFusionApi;
        ArrayList<Business> myResults;

        public YelpSearchTask()throws IOException {
            yelpFusionApiFactory = new YelpFusionApiFactory();
            //sYelpLab.get();
        }
        private ArrayList<Business> buissnessSearchTest(Map<String, String>[] mMAp)throws IOException{
            Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(mMAp[0]);
            SearchResponse response = call.execute().body();
            assertNotNull(response);
            //Log.e("Response", String.valueOf(response.getBusinesses().size()));
            for(int i =0; i<response.getBusinesses().size(); i++){
                //Log.e("Response", String.valueOf(response.getBusinesses().get(i).getName()));
            }
            return response.getBusinesses();
        }

        @Override
        protected ArrayList<Business> doInBackground(Map<String, String>[] maps) {
            try{
                yelpFusionApi = yelpFusionApiFactory.createAPI("7qCtCchb7Otc0_bGSIkucQ",
                        "PFH4bBwEqBaiuRTkrVzqFvelfwI1gRqEHzxp5orCsl0ke0ORcN4lmOLpwPP9ept5");
                myResults = new ArrayList<>();
                myResults = buissnessSearchTest(maps);
            }catch(IOException e){
                e.printStackTrace();
            }
            return myResults;
        }

        @Override
        protected void onPostExecute(ArrayList<Business> businesses) {
            Log.e("Returned Results","Post Excecute" );
            //this.mBusiness = businesses;
            sYelpLab.submitResults(businesses);
            super.onPostExecute(businesses);
        }
    }
}
