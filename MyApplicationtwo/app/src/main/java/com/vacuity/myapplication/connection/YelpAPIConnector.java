package com.vacuity.myapplication.connection;

import android.os.AsyncTask;
import android.util.Log;

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
 * Created by Gary Straub on 11/13/2017.
 */

public class YelpAPIConnector
        extends AsyncTask <Map<String, String>, Integer, ArrayList<Business>> {
    YelpFusionApiFactory yelpFusionApiFactory;
    YelpFusionApi yelpFusionApi;
    ArrayList<Business> myResults;
    YelpLab yelpLab;

    public YelpAPIConnector()throws IOException {
        yelpFusionApiFactory = new YelpFusionApiFactory();
        //yelpLab.get();
    }
    private ArrayList<Business> buissnessSearchTest(Map<String, String>[] mMAp)throws IOException{
        Map<String, String> parms = new HashMap<>();
        parms.put("term", "cat");
        parms.put("location", "sf");
        //parms.put("latitude", "40.581140");
        //parms.put("longitude", "-111.914184");
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
        YelpLab.get(null).submitResults(businesses);
        //Log.e("Status", "Process Finished");
    }

}
