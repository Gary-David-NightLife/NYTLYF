package com.vacuity.myapplication.fragment;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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
 * Created by Gary Straub on 12/10/2017.
 */

public class MapsFragment extends Fragment
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        SearchView.OnQueryTextListener{
    private GoogleMap mMap;
    MapView mMapView;
    private GPSTracker gpsTracker;
    private Location mLocation;
    double latitude, longitude;
    private Menu mMenu;
    private ArrayList<Marker> mMarkers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_maps, container, false);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refreshLocation();

        mMapView.getMapAsync(this);


        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        UiSettings uisettings = mMap.getUiSettings();
        uisettings.setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera
        defaultSearch();
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("I'm here...")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney)
                .zoom(15)
                .bearing(4)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    private void setCurrentLocationMarker(){
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("I'm here...")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney)
                .zoom(15)
                .bearing(4)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }



    public void defaultSearch(){
        Map<String, String> paramters = new HashMap<>();

        YelpLab tYelpLab = YelpLab.get(getActivity());
        if(tYelpLab.getBar()){
            paramters.put("categories", "bars");
        }
        paramters.put("sort_by", "distance");

        if(tYelpLab.getClub()){
            paramters.put("categories", "nightlife");
            paramters.put("categories", "danceclubs");
        }
        if(tYelpLab.getRest()){
            paramters.put("categories", "restaurants");
        }

        if(mLocation != null){
            paramters.put("latitude", Double.toString(mLocation.getLatitude()));
            paramters.put("longitude", Double.toString(mLocation.getLongitude()));
        } else {
            paramters.put("location", "94132");
        }
        try{
            SearchTask searchTask = new SearchTask();
            searchTask.execute(paramters);
        }catch (IOException e){
            e.printStackTrace();
        }
        Log.e("Hello", "I Exist");

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
//        Toast.makeText(getActivity(), "Map: On User Visible", Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
//        Toast.makeText(getActivity(), "Map: Info Tag", Toast.LENGTH_SHORT).show();
//        String t = marker.getTitle();
//        YelpLab tYelpLab = YelpLab.get(getActivity());
//        ArrayList<Business> myList = tYelpLab.getBusiness();
//
//        for(int i = 0; i<myList.size(); i++ ){
//            if(myList.get(i).getName() == t){
//
//                Intent intent = YelpPagerActivity.newIntent(getActivity(), myList.get(i).getId());
//                startActivity(intent);
//            }
//        }


    }

    private void refreshLocation(){
        gpsTracker = new GPSTracker(getActivity());
        mLocation = gpsTracker.getLocation();

        if(mLocation != null){
            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
        }

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        mMenu = menu;
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Search");
        searchView.setSubmitButtonEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_icon:
                refreshLocation();
                defaultSearch();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }



//        switch (item.getItemId()) {
//            case R.id.action_search:
//                Log.e("Menu", "action Search");
//                return true;
//
//            case R.id.menu_bar:
//                if(item.isChecked()){
//                    item.setChecked(false);
//                    this.searchBar = false;
//                }else{
//                    item.setChecked(true);
//                    this.searchBar = true;
//                }
//                return true;
//            case R.id.menu_club:
//                if(item.isChecked()){
//                    item.setChecked(false);
//                    this.searchClub = false;
//                }else{
//                    item.setChecked(true);
//                    this.searchClub = true;
//                }
//                return true;
//            case R.id.menu_restaurant:
//                if(item.isChecked()){
//                    item.setChecked(false);
//                    this.searchRestaurant = false;
//                }else{
//                    item.setChecked(true);
//                    this.searchRestaurant = true;
//                }
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }

    }

    private void createMarkers(ArrayList<Business> m){
        mMap.clear();
        mMarkers = new ArrayList<>();
        for(int i = 0; i<m.size(); i++){
            LatLng tmp = new LatLng(m.get(i).getCoordinates().getLatitude(),
                    m.get(i).getCoordinates().getLongitude());
//            String t = m.get(i).getCategories().get(1).getTitle();
            Marker tMarker = mMap.addMarker(new MarkerOptions().position(tmp).title(m.get(i).getName()));
            mMarkers.add(tMarker);
        }
        setCurrentLocationMarker();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.e("Search", "Something");
        Map<String, String> paramters = new HashMap<>();
        String [] queryTerms = query.split(" ");
        for(String ss : queryTerms){
            paramters.put("term", ss);
        }
        paramters.put("sort_by", "distance");
        YelpLab tYelpLab = YelpLab.get(getActivity());

        if(tYelpLab.getBar()){
            paramters.put("categories", "bars");
        }
        if(tYelpLab.getClub()){
            paramters.put("categories", "nightlife");
            paramters.put("categories", "danceclubs");

        }
        if(tYelpLab.getRest()){
            paramters.put("categories", "restaurants");
        }
        if(tYelpLab.getVenue()){
            paramters.put("categories", "musicvenues");
            paramters.put("categories", "stadiumsarenas");
        }

        if(mLocation != null){
            paramters.put("latitude", Double.toString(mLocation.getLatitude()));
            paramters.put("longitude", Double.toString(mLocation.getLongitude()));
        } else {
            paramters.put("location", "94132");
        }
        try{
            SearchTask searchTask = new SearchTask();
            searchTask.execute(paramters);
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private class SearchTask extends AsyncTask<Map<String, String>, Integer, ArrayList<Business>> {
        private ArrayList<Business> mBusiness;
        private Bitmap mBitmap;
        private Location mLocation;
        YelpFusionApiFactory yelpFusionApiFactory;
        YelpFusionApi yelpFusionApi;
        ArrayList<Business> myResults;

        public SearchTask()throws IOException {
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
            YelpLab tYelpLab = YelpLab.get(getActivity());
            tYelpLab.submitResults(businesses);
            createMarkers(businesses);
            super.onPostExecute(businesses);
        }
    }
}
