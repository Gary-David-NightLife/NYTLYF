package com.vacuity.myapplication.controller;

/**
 * Created by Gary Straub on 7/23/2017.
 */

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.vacuity.myapplication.ApiConfig;
import com.vacuity.myapplication.app.App;
import com.vacuity.myapplication.connection.YelpAPITest;
import com.vacuity.myapplication.model.MeetUp;
import com.vacuity.myapplication.request.JsonRequest;
import com.vacuity.myapplication.volley.VolleySingleton;

import java.io.IOException;
import java.util.List;

public class JsonController {
    private final int TAG = 100;

    private OnResponseListener responseListener;
    YelpAPITest yelpAPITest;


    public JsonController(OnResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public void request(){
        try {
            int cats = 4;
            yelpAPITest = new YelpAPITest();
            yelpAPITest.execute();
            //yelpAPITest.buissnessSearchTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    public void sendRequest(String query){


        // Request Method
        int method = Request.Method.GET;

        // Url with GET parameters
        String url = ApiConfig.getData(query);




        // Create new request using JsonRequest

        JsonRequest request
                = new JsonRequest(
                method,
                url,
                new Response.Listener<List<MeetUp>>() {
                    @Override
                    public void onResponse(List<MeetUp> myMeetUps) {

                        responseListener.onSuccess(myMeetUps);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseListener.onFailure(error.getMessage());
                    }
                }
        );

        // Add tag to request
        request.setTag(TAG);

        // Get RequestQueue from VolleySingleton
        VolleySingleton.getInstance(App.getContext()).addToRequestQueue(request);
    }
    public void cancelAllRequests() {
        VolleySingleton.getInstance(App.getContext()).cancelAllRequests(TAG);
    }

    public interface OnResponseListener {
        void onSuccess(List<MeetUp> myMeetUps);
        void onFailure(String errorMessage);
    }
}
