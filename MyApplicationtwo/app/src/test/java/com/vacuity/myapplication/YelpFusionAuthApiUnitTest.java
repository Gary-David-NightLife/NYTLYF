package com.vacuity.myapplication;


import com.vacuity.myapplication.connection.YelpFusionApiFactory;
import com.vacuity.myapplication.models.AccessToken;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Ranga on 2/24/2017.
 */

public class YelpFusionAuthApiUnitTest {
    YelpFusionApiFactory yelpFusionApiFactory;

    public YelpFusionAuthApiUnitTest(){
        yelpFusionApiFactory = new YelpFusionApiFactory();
    }

    @Test
    public void AccessTokenTest() throws IOException {
        AccessToken accessToken = yelpFusionApiFactory.getAccessToken(Credential.appId(), Credential.appSecret());
        assertNotNull(accessToken);
    }
}
