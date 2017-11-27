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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vacuity.myapplication.R;
import com.vacuity.myapplication.connection.YelpFusionApi;
import com.vacuity.myapplication.connection.YelpFusionApiFactory;
import com.vacuity.myapplication.model.YelpLab;
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

public class MapFragment extends SupportMapFragment{

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
    private YelpLab yelpLab;
    private ArrayList<Business> currentLocations;


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
        paramters.put("categories", "bar");
        try{
            SearchTask searchTask = new SearchTask();
            searchTask.execute(paramters);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void setResults(ArrayList<Business> myResults){
        this.currentLocations = myResults;
        updateUI();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

      //  locationManager.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER, 5000, 10, new MyLocationListener());

        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
        yelpLab = YelpLab.get(getActivity());

        //mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mClient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                        renewLocation();
                        defaultSearch();
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

        inflater.inflate(R.menu.fragment_locatr, menu);

        MenuItem searchItem = menu.findItem(R.id.action_locate);
        searchItem.setEnabled(mClient.isConnected());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_locate:
                if (hasLocationPermission()) {
                    renewLocation();
                    updateUI();
                } else {
                    requestPermissions(LOCATION_PERMISSIONS,
                            REQUEST_LOCATION_PERMISSIONS);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                        defaultSearch();
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
        ArrayList<LatLng> pList = new ArrayList<>();
        if(currentLocations != null){
            for(int i = 0; i< currentLocations.size(); i++) {
                LatLng tmp = new LatLng(currentLocations.get(i).getCoordinates().getLatitude(),
                        currentLocations.get(i).getCoordinates().getLongitude());
                pList.add(tmp);
//                LatLng myPoint = new LatLng(currentLocations.get(i).getCoordinates().getLatitude(),
//                        currentLocations.get(i).getCoordinates().getLatitude());
                Log.e("MarkerGPS", String.valueOf(currentLocations.get(i).getCoordinates().getLatitude()));
                mMap.addMarker(new MarkerOptions().position(pList.get(i)).title(currentLocations.get(i).getName()));
            }

        }
        LatLng sydney = new LatLng(37.723894, -122.479274);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        if(mCurrentLocation!=null){
            LatLng myPoint = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(myPoint).title("Meeeee")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }else {
            Log.e("Marker", "Location Marker not set");
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        LatLng itemPoint = new LatLng(mMapItem.getCoordinates().getLatitude(), mMapItem.getCoordinates().getLongitude());
        //LatLng sydney = new LatLng(lat, lon);
//        LatLng myPoint = new LatLng(
//                mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

        //BitmapDescriptor itemBitmap = BitmapDescriptorFactory.fromBitmap(mMapImage);
        //MarkerOptions itemMarker = new MarkerOptions().position(itemPoint).icon(itemBitmap);
        //MarkerOptions myMarker = new MarkerOptions().position(myPoint);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.clear();
        //mMap.addMarker(itemMarker);
        //mMap.addMarker(myMarker);

//        LatLngBounds bounds = new LatLngBounds.Builder()
//                .include(itemPoint)
//                .include(myPoint)
//                .build();

        //int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
//        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
//        mMap.animateCamera(update);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
            //yelpLab.get();
        }
        private ArrayList<Business> buissnessSearchTest(Map<String, String>[] mMAp)throws IOException{
            Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(mMAp[0]);
            SearchResponse response = call.execute().body();
            assertNotNull(response);
            //Log.e("Response", String.valueOf(response.getBusinesses().size()));
            for(int i =0; i<response.getBusinesses().size(); i++){
                Log.e("Response", String.valueOf(response.getBusinesses().get(i).getName()));
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
            yelpLab.submitResults(businesses);
            setResults(businesses);
            super.onPostExecute(businesses);
        }
    }



}
