package com.vacuity.myapplication.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vacuity.myapplication.R;

/**
 * Created by Gary Straub on 12/10/2017.
 */

public class MapsFragment extends Fragment
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        NavigationView.OnNavigationItemSelectedListener{
    private GoogleMap mMap;
    MapView mMapView;
    private Boolean searchBar;
    private Boolean searchClub;
    private Boolean searchRestaurant;
    private GPSTracker gpsTracker;
    private Location mLocation;
    double latitude, longitude;
    private Menu mMenu;
    private DrawerLayout mDrawerLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_maps, container, false);

        mDrawerLayout = (DrawerLayout) v.findViewById(R.id.main_layout);



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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("I'm here..."));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getActivity(), "Map: On User Visible", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getActivity(), "Map: Info Tag", Toast.LENGTH_SHORT).show();
    }

    private void refreshLocation(){
        gpsTracker = new GPSTracker(getActivity());
        mLocation = gpsTracker.getLocation();

        latitude = mLocation.getLatitude();
        longitude = mLocation.getLongitude();
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
        //searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Search");
        searchView.setSubmitButtonEnabled(true);
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
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_bar) {
            Toast.makeText(getActivity(), "Bars", Toast.LENGTH_SHORT).show();
            Log.e("Drawer Menu", "Bars");
            // Handle the camera action
        } else if (id == R.id.nav_restaurant) {
            Toast.makeText(getActivity(), "Clubs", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_restaurant) {
            Toast.makeText(getActivity(), "Restaurants", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_Favorites) {
            Toast.makeText(getActivity(), "Favorites", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_refresh) {
            Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();

        }

        //mDrawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        //mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
