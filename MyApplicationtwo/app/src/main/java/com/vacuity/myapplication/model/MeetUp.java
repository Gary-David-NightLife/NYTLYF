package com.vacuity.myapplication.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Created by Gary Straub on 7/23/2017.
 */

import static com.android.volley.VolleyLog.TAG;

public class MeetUp {
    private String mTitle;
    private String mDescription;
    private String mImg;
    private UUID mID;
    private int ID;
    double mLat;
    double mLong;


    public static List<MeetUp> parseJASON(JSONArray jsonArray) throws JSONException{
        List<MeetUp> myMeetUps = new ArrayList<>();
        try{
            MeetUp meetup;
            JSONObject object;
            String image="";
            final int n = jsonArray.length();
            for (int i = 0; i < n; ++i) {
                object = jsonArray.getJSONObject(i);
                if(object.has("key_photo")){
                    if(((JSONObject)object.get("key_photo")).get("photo_link")!=null) {
                        image = ((JSONObject) object.get("key_photo")).getString("photo_link");
                    }
                }
                else if(object.has("photos")) {
                    if(((JSONArray)object.get("photos")).getJSONObject(0).has("photo_link")) {
                        image = ((JSONArray)object.get("photos")).getJSONObject(0).getString("photo_link");
                    }
                }

                meetup = new MeetUp(object.getString("name"),object.getString("description"),image);

                if(object.has("id"))
                    meetup.setID(object.getInt("id"));
                if(object.has("lat"))
                    meetup.setmLat(object.getDouble("lat"));
                if(object.has("lon"))
                    meetup.setmLong(object.getDouble("lon"));

                myMeetUps.add(meetup);
            }
        }
        catch (JSONException e){
            Log.e(TAG, e.getMessage());
        }
        Log.i(TAG, "List created");
        return myMeetUps;

    }
    private MeetUp (String name, String desc, String imgURL){
        setmID();
        setmTitle(name);
        setmDescription(desc);
        setmImg(imgURL);
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmImg() {
        return mImg;
    }

    public void setmImg(String mImg) {
        this.mImg = mImg;
    }

    public UUID getmID() {
        return mID;
    }

    public void setmID() {
        this.mID = UUID.randomUUID();
    }

    public double getmLat() {
        return mLat;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    public double getmLong() {
        return mLong;
    }

    public void setmLong(double mLong) {
        this.mLong = mLong;
    }
}
