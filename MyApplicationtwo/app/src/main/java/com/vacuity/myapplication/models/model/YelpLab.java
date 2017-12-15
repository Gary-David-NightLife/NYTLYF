package com.vacuity.myapplication.models.model;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.vacuity.myapplication.connection.YelpAPIConnector;
import com.vacuity.myapplication.models.Business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gary Straub on 11/13/2017.
 */

public class YelpLab {
    private static ArrayList<Business> mBuissnessList;
    private static YelpLab sYelpLab;
    //private YelpAPIConnector yelpAPIConnector;
    private static Location mLocation;
    private Boolean mBar;
    private Boolean mClub;
    private Boolean mVenue;
    private Boolean mRest;

    public static YelpLab get(Context context){
        if(sYelpLab == null){
            sYelpLab = new YelpLab(context);
        }

        return sYelpLab;
    }

    private YelpLab(Context context){
        mBuissnessList = new ArrayList<>();
        setFalse();
        try {
            YelpAPIConnector yelpAPIConnector = new YelpAPIConnector();
            //newYelpSearch();
            //Log.e("Businesses", mBuissness.get(0).getName());
            //yelpAPITest.buissnessSearchTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void newYelpSearch(){
        Map<String, String> parms = new HashMap<>();
        parms.put("term", "Restaurant");
        parms.put("location", "sf");
        //parms.put("latitude", "40.581140");
        //parms.put("longitude", "-111.914184");
        try{
            YelpAPIConnector yelpAPIConnector = new YelpAPIConnector();
            yelpAPIConnector.execute(parms);
        }catch (IOException e){
            e.printStackTrace();
        }


    }
    public void newYelpSearch(Map<String, String> mMap){
        try{
            YelpAPIConnector yelpAPIConnector = new YelpAPIConnector();
            yelpAPIConnector.execute(mMap);
        }catch (IOException e){
            e.printStackTrace();
        }    }
    public void submitResults(ArrayList<Business> mResults){
        mBuissnessList = mResults;
    }
    public Business getBusiness(String id){
        for (Business mBus : mBuissnessList) {
            if (mBus.getId().equals(id)) {
                return mBus;
            }
        }
        return null;
    }
    public ArrayList<Business> getBusiness(){
        if(mBuissnessList.isEmpty()){
            Log.e("Attempt Made", "Retrieve Businesses EMPTY");
            return null;
        }else {
            //Log.e("Attempt Made", String.valueOf(mBuissnessList.size()));
            //Log.e("Attempt Made", "Retrieve Businesses");
            return mBuissnessList;
        }
    }

    public void setMyLocation(Location l){
        mLocation = l;
    }
    public void getSearchCategories(){

    }
    public Location getMyLocation(){
        return mLocation;
    }

    public void setClub(Boolean b){
        mClub = b;
    }
    public void setBar(Boolean b){
        mBar = b;
    }
    public void setRest(Boolean b){
        mRest = b;
    }
    public Boolean getClub(){
        return mClub;
    }
    public Boolean getBar(){
        return mBar;
    }
    public void setVenue(Boolean b){
        mVenue = b;
    }
    public Boolean getVenue(){
        return mVenue;
    }
    public Boolean getRest(){
        return mRest;
    }
    private void setFalse(){
        mRest = false;
        mBar = false;
        mClub = false;
        mVenue = false;
    }
}
