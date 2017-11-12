package com.vacuity.myapplication;


import com.vacuity.myapplication.connection.YelpFusionApi;
import com.vacuity.myapplication.connection.YelpFusionApiFactory;
import com.vacuity.myapplication.models.AutoComplete;
import com.vacuity.myapplication.models.Business;
import com.vacuity.myapplication.models.Reviews;
import com.vacuity.myapplication.models.SearchResponse;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Ranga on 2/22/2017.
 */

public class YelpFusionApiUnitTest {
    YelpFusionApiFactory yelpFusionApiFactory;
    YelpFusionApi yelpFusionApi;

    public YelpFusionApiUnitTest() throws IOException {
        yelpFusionApiFactory = new YelpFusionApiFactory();
        yelpFusionApi = yelpFusionApiFactory.createAPI("7qCtCchb7Otc0_bGSIkucQ",
                "PFH4bBwEqBaiuRTkrVzqFvelfwI1gRqEHzxp5orCsl0ke0ORcN4lmOLpwPP9ept5");
    }

    @Test
    public void businessSearchTest() throws IOException {
        Map<String, String> parms = new HashMap<>();
        parms.put("term", "indian food");
        parms.put("latitude", "40.581140");
        parms.put("longitude", "-111.914184");
        Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(parms);
        SearchResponse response = call.execute().body();
        assertNotNull(response);

        //Log.i("Hello",response.getBusinesses().get(0).getDisplayPhone());

    }

    @Test
    public void businessPhoneSearchTest() throws IOException {
        Call<SearchResponse> call = yelpFusionApi.getPhoneSearch("+18014384823");
        SearchResponse response = call.execute().body();
        assertNotNull(response);
    }

    @Test
    public void businessTest() throws IOException {
        Call<Business> call = yelpFusionApi.getBusiness("saffron-valley-south-jordan");
        Business response = call.execute().body();
        assertNotNull(response);
    }

    @Test
    public void businessReviewsTest() throws IOException {
        Call<Reviews> call = yelpFusionApi.getBusinessReviews("saffron-valley-south-jordan", null);
        Reviews response = call.execute().body();
        assertNotNull(response);
    }

    @Test
    public void autoCompleteTest() throws IOException {
        Map<String, String> parms = new HashMap<>();
        parms.put("text", "saffron");
        parms.put("latitude", "40.581140");
        parms.put("longitude", "-111.914184");
        Call<AutoComplete> call = yelpFusionApi.getAutocomplete(parms);
        AutoComplete response = call.execute().body();
        assertNotNull(response);
    }
}
