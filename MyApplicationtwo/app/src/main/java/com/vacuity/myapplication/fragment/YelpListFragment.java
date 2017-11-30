package com.vacuity.myapplication.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vacuity.myapplication.R;
import com.vacuity.myapplication.activity.YelpPagerActivity;
import com.vacuity.myapplication.app.App;
import com.vacuity.myapplication.connection.YelpFusionApi;
import com.vacuity.myapplication.connection.YelpFusionApiFactory;
import com.vacuity.myapplication.models.Business;
import com.vacuity.myapplication.models.SearchResponse;
import com.vacuity.myapplication.models.model.YelpLab;
import com.vacuity.myapplication.volley.VolleySingleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Gary Straub on 11/13/2017.
 */

public class YelpListFragment extends Fragment
        implements
        SearchView.OnQueryTextListener{

    private RecyclerView mYelpRecyclerView;
    private YelpAdapter mAdapter;
    private Boolean searchBar;
    private Boolean searchClub;
    private Boolean searchRestaurant;
    private YelpLab sYelpLab;
    private ArrayList<Business> currentLocations;
    private Menu mMenu;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //Toast.makeText(getActivity(), "Map: On User Visible", Toast.LENGTH_SHORT).show();
            getList();
            updateUI();
        }
    }

    public void setResults(ArrayList<Business> r){
        YelpLab tYelpLab = YelpLab.get(getActivity());
        tYelpLab.submitResults(r);
        currentLocations = r;
        for(int i = 0; i< currentLocations.size(); i++){
            Log.e("Results Print", currentLocations.get(i).getName());
        }
        updateUI();
    }

    private void getList(){
        YelpLab tYelpLab = YelpLab.get(getActivity());
        currentLocations = tYelpLab.getBusiness();

        //currentLocations = sYelpLab.getBusiness();
        if(currentLocations == null){
            Log.e("Testing", "Default Search");
            defaultSearch();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLocations = new ArrayList<>();
        sYelpLab = YelpLab.get(getActivity());
        getList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.yelp_list_fragment, container, false);
        setHasOptionsMenu(true);
        mYelpRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mYelpRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getList();
        updateUI();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        mMenu = menu;
        searchBar = menu.findItem(R.id.menu_bar).isChecked();
        searchClub = menu.findItem(R.id.menu_club).isChecked();
        searchRestaurant = menu.findItem(R.id.menu_restaurant).isChecked();
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Search");
        searchView.setSubmitButtonEnabled(true);
        //searchView.setOnQueryTextListener(true);
    }

    private void defaultSearch(){
        Map<String, String> paramters = new HashMap<>();
        paramters.put("location", "94132");
        paramters.put("categories", "danceclubs");
        paramters.put("categories", "nightlife");
        try{
            YelpSearchTask searchTask = new YelpSearchTask();
            searchTask.execute(paramters);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
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
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Log.e("Menu", "action Search");
                return true;

            case R.id.menu_bar:
                if(item.isChecked()){
                    item.setChecked(false);
                    this.searchBar = false;
                }else{
                    item.setChecked(true);
                    this.searchBar = true;
                }
                return true;
            case R.id.menu_club:
                if(item.isChecked()){
                    item.setChecked(false);
                    this.searchClub = false;
                }else{
                    item.setChecked(true);
                    this.searchClub = true;
                }
                return true;
            case R.id.menu_restaurant:
                if(item.isChecked()){
                    item.setChecked(false);
                    this.searchRestaurant = false;
                }else{
                    item.setChecked(true);
                    this.searchRestaurant = true;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





    private void updateUI() {
        if(currentLocations !=null) {
            if (mAdapter == null) {
                mAdapter = new YelpAdapter(currentLocations);
                if (currentLocations != null) {
                    mAdapter.updateDataset(currentLocations);
                }
                mYelpRecyclerView.setAdapter(mAdapter);
            } else {
                if (currentLocations != null) {
                    mAdapter.updateDataset(currentLocations);
                }

                mAdapter.notifyDataSetChanged();
            }

            if (currentLocations != null) {
                mAdapter.updateDataset(currentLocations);
            }
        }
        //mYelpRecyclerView.setAdapter(mAdapter);
    }

    private class YelpHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Business mYelp;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private NetworkImageView mSolvedImageView;

        public YelpHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.meetup_card_layout, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.Title);
            mDateTextView = (TextView) itemView.findViewById(R.id.meetup_description);
            mSolvedImageView = (NetworkImageView) itemView.findViewById(R.id.nivPoster);
        }

        public void bind(Business myYelp) {
            mYelp = myYelp;
            mTitleTextView.setText(mYelp.getName());
            mDateTextView.setText(mYelp.getPhone().toString());
            ImageLoader imageLoader = VolleySingleton.getInstance(App.getContext()).getImageLoader();
            mSolvedImageView.setImageUrl(myYelp.getImageUrl(), imageLoader);
        }

        @Override
        public void onClick(View view) {
            Intent intent = YelpPagerActivity.newIntent(getActivity(), mYelp.getId());
            startActivity(intent);
        }

    }

    private class YelpAdapter extends RecyclerView.Adapter<YelpHolder> {

        private List<Business> myYelps;

        public YelpAdapter(List<Business> myBus) {
            myYelps = myBus;
        }

        public void updateDataset(ArrayList<Business> modelList){
            this.myYelps.clear();
            this.myYelps.addAll(modelList);
        }

        @Override
        public YelpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new YelpHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(YelpHolder holder, int position) {
            Business myBus = myYelps.get(position);
            holder.bind(myBus);
        }

        @Override
        public int getItemCount() {
            return myYelps.size();
        }
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
            setResults(businesses);
            super.onPostExecute(businesses);
        }
    }
}
