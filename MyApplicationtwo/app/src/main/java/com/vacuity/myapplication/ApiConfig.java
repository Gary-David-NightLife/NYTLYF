package com.vacuity.myapplication;

/**
 * Created by Gary Straub on 7/22/2017.
 */

import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.vacuity.myapplication.app.App;
import com.vacuity.myapplication.connection.YelpFusionApi;
import com.vacuity.myapplication.connection.YelpFusionApiFactory;
import com.vacuity.myapplication.models.SearchResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;

public class ApiConfig {

    YelpFusionApiFactory yelpFusionApiFactory;
    YelpFusionApi yelpFusionApi;

//    private static int PAGE_SIZE = 20;
//    private static String KEY_MEETUP = "39565db356f354976116a2d1d436143";
    private static String HIT_MEETUP = "https://api.meetup.com/find/groups?&sign=true&photo-host=secure";
    private static int PAGE_SIZE = 20;
    private static String KEY_MEETUP = "39565db356f354976116a2d1d436143";
//    private static String HIT_MEETUP = "https://api.meetup.com/topics?search=";

    private static String CLIENT_ID = "7qCtCchb7Otc0_bGSIkucQ";
    private static String CLIENT_SECRET =
            "PFH4bBwEqBaiuRTkrVzqFvelfwI1gRqEHzxp5orCsl0ke0ORcN4lmOLpwPP9ept5";
    private static String SAMPLE_REQUEST = "https://api.yelp.com/v2/search?term=yelp&location=San+Francisco&oauth_consumer_key=MZnv7kUU0YsBuUt3teqk9JwqETiAr7rrx-5NTYxUVQ8MnpwOSji5ohfFjB3D9tz4zsTNbCfjOp_lRbu_7cFdo1eQKF1qTXzT3mFmtau7NJ5e__TFdn9MumvPaHgEWnYx";
    private static String BASE_URL = "https://api.yelp.com/v2/search?";
    private static String accTok = "https://api.yelp.com/oauth2/token";
    private static String ret = "{\n" +
            "    \"access_token\": \"MZnv7kUU0YsBuUt3teqk9JwqETiAr7rrx-5NTYxUVQ8MnpwOSji5ohfFjB3D9tz4zsTNbCfjOp_lRbu_7cFdo1eQKF1qTXzT3mFmtau7NJ5e__TFdn9MumvPaHgEWnYx\",\n" +
            "    \"expires_in\": 637237972,\n" +
            "    \"token_type\": \"Bearer\"\n" +
            "}";
    private OkHttpClient httpClient;


    public void setUP()throws IOException{
        yelpFusionApiFactory = new YelpFusionApiFactory();
        yelpFusionApi = yelpFusionApiFactory.createAPI(CLIENT_ID, CLIENT_SECRET);

    }

    public void search()throws IOException{
        setUP();
        Map<String, String> parms = new HashMap<>();
        parms.put("term", "indian food");
        parms.put("latitude", "40.581140");
        parms.put("longitude", "-111.914184");
        Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(parms);
        SearchResponse response = call.execute().body();
        response.getBusinesses();
    }


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




        //return SAMPLE_REQUEST;
    }
}
