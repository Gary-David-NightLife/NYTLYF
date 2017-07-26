package com.vacuity.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.vacuity.myapplication.R;

/**
 * Created by Gary Straub on 7/24/2017.
 */

public class MapFragment extends Fragment  {
    private GoogleMap mMap;

    public MapFragment(){

    }
    public static MapFragment newInstance(){
        MapFragment fragment = new MapFragment();
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_meetup, container, false);
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync((OnMapReadyCallback) this);

        return v;


    }

}
