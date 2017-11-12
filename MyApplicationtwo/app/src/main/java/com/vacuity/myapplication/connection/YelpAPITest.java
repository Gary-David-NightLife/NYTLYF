package com.vacuity.myapplication.connection;

import android.os.AsyncTask;
import android.util.Log;

import com.vacuity.myapplication.models.AccessToken;
import com.vacuity.myapplication.models.Business;
import com.vacuity.myapplication.models.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Gary Straub on 11/9/2017.
 */

public class YelpAPITest extends AsyncTask {
    YelpFusionApiFactory yelpFusionApiFactory;
    YelpFusionApi yelpFusionApi;
    ArrayList<Business> myResults;
    private AccessToken accessToken;
    private String appID = "7qCtCchb7Otc0_bGSIkucQ";
    private String appSecret = "PFH4bBwEqBaiuRTkrVzqFvelfwI1gRqEHzxp5orCsl0ke0ORcN4lmOLpwPP9ept5";

    public YelpAPITest()throws IOException {
        yelpFusionApiFactory = new YelpFusionApiFactory();
        //yelpFusionApi = yelpFusionApiFactory.createAPI("7qCtCchb7Otc0_bGSIkucQ", "PFH4bBwEqBaiuRTkrVzqFvelfwI1gRqEHzxp5orCsl0ke0ORcN4lmOLpwPP9ept5");

//        accessToken = yelpFusionApiFactory.getAccessToken(appID, appSecret);
//        assertNotNull(accessToken);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try{
            yelpFusionApi = yelpFusionApiFactory.createAPI("7qCtCchb7Otc0_bGSIkucQ", "PFH4bBwEqBaiuRTkrVzqFvelfwI1gRqEHzxp5orCsl0ke0ORcN4lmOLpwPP9ept5");
            myResults = new ArrayList<>();
            myResults = buissnessSearchTest();
        }catch(IOException e){
            e.printStackTrace();
        }
        return myResults;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    public ArrayList<Business> getResults(){
        //Log.e("Businesses", myResults.get(1).getName());
        return myResults;
    }


    public ArrayList<Business> buissnessSearchTest()throws IOException{
        Map<String, String> parms = new HashMap<>();
        parms.put("term", "Restaurant");
        parms.put("location", "sf");
        //parms.put("latitude", "40.581140");
        //parms.put("longitude", "-111.914184");
        Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(parms);
        SearchResponse response = call.execute().body();
        assertNotNull(response);
        Log.e("Response", String.valueOf(response.getBusinesses().size()));
        for(int i =0; i<response.getBusinesses().size(); i++){
            Log.e("Response", String.valueOf(response.getBusinesses().get(i).getName()));
        }
        return response.getBusinesses();
    }
}
