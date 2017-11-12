package com.vacuity.myapplication.request;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.vacuity.myapplication.model.MeetUp;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by Gary Straub on 7/23/2017.
 */

public class JsonRequest extends Request<List<MeetUp>>{


    private Response.Listener<List<MeetUp>> successListener;
    //YelpAPITest yelpAPITest;

    public JsonRequest (int method, String url,
                        Response.Listener<List<MeetUp>> successListener,
                        Response.ErrorListener errorListener){

        super(method, url, errorListener);
        this.successListener = successListener;
    }


    protected Response<List<MeetUp>> parseNetworkResponse(NetworkResponse response){
        String jsonString = new String(response.data);
        List<MeetUp> myMeetUps;
        JSONArray jsonArray;
        Log.i("JSON Response from API", jsonString);
        try {
            jsonArray = new JSONArray(jsonString);
            Log.e("JSON", jsonString);
            myMeetUps = MeetUp.parseJASON(jsonArray);
        }
        catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new VolleyError("Failed to process the request"));
        }
        return Response.success(myMeetUps, getCacheEntry());
    }
    protected void deliverResponse(List<MeetUp> myMeetUps){
        successListener.onResponse(myMeetUps);
    }


}
