package com.vacuity.myapplication.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vacuity.myapplication.R;
import com.vacuity.myapplication.connection.YelpFusionApi;
import com.vacuity.myapplication.connection.YelpFusionApiFactory;
import com.vacuity.myapplication.models.model.YelpLab;
import com.vacuity.myapplication.models.Business;
import com.vacuity.myapplication.models.SearchResponse;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Gary Straub on 7/24/2017.
 */

public class MapFragment extends SupportMapFragment
        implements
        SearchView.OnQueryTextListener {

    private static final String TAG = "MapFragment";
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    private GoogleApiClient mClient;
    private GoogleMap mMap;
    //private LocationServices mFusedLocationClient;
    private Bitmap mMapImage;
    private Business mMapItem;
    private Location mCurrentLocation;
    private YelpLab sYelpLab;
    private Boolean searchBar;
    private Boolean searchClub;
    private Boolean searchRestaurant;
    private ArrayList<Business> currentLocations;
    private Menu mMenu;


    public static MapFragment newInstance(){
        return new MapFragment();
    }

    private void defaultSearch(){
        Map<String, String> paramters = new HashMap<>();
        if(mCurrentLocation!=null){
            paramters.put("latitude", String.valueOf(mCurrentLocation.getLatitude()));
            paramters.put("longitude", String.valueOf(mCurrentLocation.getLongitude()));
        }else{
            paramters.put("location", "94132");
        }
        paramters.put("categories", "danceclubs");
        paramters.put("categories", "nightlife");
        try{
            SearchTask searchTask = new SearchTask();
            searchTask.execute(paramters);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void setResults(ArrayList<Business> r){
        currentLocations = r;
        for(int i=0; i<currentLocations.size();i++){
            //Log.e("Results Print", myRes.get(i).getName());
        }
        updateUI();
    }

    private void getList(){
        currentLocations = sYelpLab.getBusiness();
        if(currentLocations == null){
            defaultSearch();
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

      //  locationManager.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER, 5000, 10, new MyLocationListener());

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        sYelpLab = YelpLab.get(getActivity());

        //mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mClient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                        //renewLocation();
                        getList();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                updateUI();
            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();

        getActivity().invalidateOptionsMenu();

        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        mClient.disconnect();
    }

    @Override
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_locate:
//                if (hasLocationPermission()) {
//                    renewLocation();
//                    updateUI();
//                } else {
//                    requestPermissions(LOCATION_PERMISSIONS,
//                            REQUEST_LOCATION_PERMISSIONS);
//                }
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.e("Menu", "Query Search");
        Map<String, String> paramters = new HashMap<>();
        String [] queryTerms = query.split(" ");
        for(String ss : queryTerms){
            paramters.put("term", ss);
        }
        paramters.put("location", "94132");

        try{
            //MapFragment.SearchTask searchTask = new MapFragment.SearchTask();
            SearchTask searchTask = new SearchTask();
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                if (hasLocationPermission()) {
                    //findImage();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @SuppressLint("MissingPermission")
    private void renewLocation() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new LocationListener() {

                    @Override
                    public void onLocationChanged(Location location) {
                        Log.e(TAG, "Got a fix: " + location);
                        mCurrentLocation = location;
                        updateUI();
                    }
                });
    }
    private boolean hasLocationPermission() {
        int result = ContextCompat
                .checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void updateUI() {
        if (mMap == null ) {
            return;
        }
        getList();
        ArrayList<LatLng> pList = new ArrayList<>();
        float dist = 99999999;
        Business tmpClose;
        int pos = 0;
        float distance = 0;
        if(currentLocations != null){
            for(int i = 0; i< currentLocations.size(); i++) {
                LatLng tmp = new LatLng(currentLocations.get(i).getCoordinates().getLatitude(),
                        currentLocations.get(i).getCoordinates().getLongitude());
                pList.add(tmp);
                Location locationB = new Location("Point");
                locationB.setLatitude(currentLocations.get(i).getCoordinates().getLatitude());
                locationB.setLongitude(currentLocations.get(i).getCoordinates().getLongitude());
                if(mCurrentLocation!=null){
                    distance = mCurrentLocation.distanceTo(locationB);
                    if(dist > distance){
                        dist = distance;
                        pos = i;
                    }
                }

//                LatLng myPoint = new LatLng(currentLocations.get(i).getCoordinates().getLatitude(),
//                        currentLocations.get(i).getCoordinates().getLatitude());
                //Log.e("MarkerGPS", String.valueOf(currentLocations.get(i).getCoordinates().getLatitude()));
                mMap.addMarker(new MarkerOptions().position(pList.get(i)).title(currentLocations.get(i).getName()));
            }
        }
        Log.e("Closest Dist", Float.toString(dist));

        LatLng sydney = new LatLng(37.723894, -122.479274);

        mMap.addMarker(new MarkerOptions().position(sydney).title("SF State").icon(BitmapDescriptorFactory
        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        if(mCurrentLocation!=null){
            LatLng myPoint = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(myPoint).title("Meeeee")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }else {
            Log.e("Marker", "Location Marker not set");
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney)
                .zoom(15)
                .bearing(4)
                .tilt(30)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

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
            sYelpLab.submitResults(businesses);
            setResults(businesses);
            super.onPostExecute(businesses);
        }
    }



}
