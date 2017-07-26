package com.vacuity.myapplication;

import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.vacuity.myapplication.app.App;

/**
 * Created by Gary Straub on 7/22/2017.
 */



public class ApiConfig {


//    private static int PAGE_SIZE = 20;
//    private static String KEY_MEETUP = "39565db356f354976116a2d1d436143";
    private static String HIT_MEETUP = "https://api.meetup.com/find/groups?&sign=true&photo-host=secure";
    private static int PAGE_SIZE = 20;
    private static String KEY_MEETUP = "39565db356f354976116a2d1d436143";
//    private static String HIT_MEETUP = "https://api.meetup.com/topics?search=";
    public static String getData(String query) {

        StringBuilder recommend = new StringBuilder(HIT_MEETUP);
        recommend.append("&page=");
        recommend.append(PAGE_SIZE);
        recommend.append("&key=");
        recommend.append(KEY_MEETUP);
        Location lastLocation = App.getLastLocation();

        if(lastLocation != null) {
            recommend.append("&radius=smart&lat=");
            recommend.append(String.valueOf(lastLocation.getLatitude()));
            recommend.append("&lon=");
            recommend.append(String.valueOf(lastLocation.getLongitude()));
        }

        if(query.length() > 1) {
            recommend.append("&text=");
            recommend.append(Uri.encode(query));
        }
        Log.d("I HAVE RUN", recommend.toString());


        //String url= "https://api.meetup.com/topics?search=" + Uri.encode(query) +"&key=2b73754f545e331f17273651a16465";
        return recommend.toString();
    }

}
