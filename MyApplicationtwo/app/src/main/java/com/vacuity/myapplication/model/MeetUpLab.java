package com.vacuity.myapplication.model;

import com.vacuity.myapplication.connection.YelpAPITest;
import com.vacuity.myapplication.models.Business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gary Straub on 7/24/2017.
 */

public class MeetUpLab {
    private  List<MeetUp> mMeetUps;
    private static MeetUpLab sMeetUpLab;
    YelpAPITest yelpAPITest;
    private ArrayList<Business> mBuissness;


    public MeetUpLab get(){
        if(sMeetUpLab == null){
            sMeetUpLab = new MeetUpLab();
        }

        return sMeetUpLab;
    }
    public MeetUpLab(){
        mMeetUps = new ArrayList<>();
        mBuissness = new ArrayList<>();
        try {
            yelpAPITest = new YelpAPITest();
            yelpAPITest.execute();
            mBuissness = yelpAPITest.getResults();
            //Log.e("Businesses", mBuissness.get(0).getName());
            //yelpAPITest.buissnessSearchTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MeetUp> getList(){
        return mMeetUps;
    }
    public MeetUp getMeetUp(UUID id){
        for(MeetUp tMU : mMeetUps){
            if(tMU.getmID().equals(id)){
                return tMU;
            }
        }
        return null;
    }
    public void setLIST(List<MeetUp> newList){
    mMeetUps = newList;
    }
    public ArrayList<Business> getBus(){
        return mBuissness;
    }


}
