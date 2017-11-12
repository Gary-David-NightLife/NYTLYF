package com.vacuity.myapplication.connection;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vacuity.myapplication.connection.interceptors.AccessTokenInterceptor;
import com.vacuity.myapplication.exception.ErrorHandlingInterceptor;
import com.vacuity.myapplication.models.AccessToken;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Ranga on 2/22/2017.
 */

public class YelpFusionApiFactory {
    private static final String YELP_API_BASE_URL = "https://api.yelp.com";

    private OkHttpClient httpClient;
    private OkHttpClient authClient;
    private AccessToken accessToken;
    private String appID = "7qCtCchb7Otc0_bGSIkucQ";
    private String appSecret = "PFH4bBwEqBaiuRTkrVzqFvelfwI1gRqEHzxp5orCsl0ke0ORcN4lmOLpwPP9ept5";

    public YelpFusionApiFactory() {}

    public YelpFusionApi createAPI(String clientId, String clientSecret) throws IOException {
        getAccessToken(appID, appSecret);

        return getYelpFusionApi();
    }

    public YelpFusionApi createAPI(String accessToken) throws IOException {
        this.accessToken = new AccessToken();
        this.accessToken.setAccessToken(accessToken);
        this.accessToken.setTokenType("Bearer");
        return getYelpFusionApi();
    }

    private YelpFusionApi getYelpFusionApi() {
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new AccessTokenInterceptor(accessToken))
                .addInterceptor(new ErrorHandlingInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getAPIBaseUrl())
                .addConverterFactory(getJacksonFactory())
                .client(this.httpClient)
                .build();
        return retrofit.create(YelpFusionApi.class);
    }

    public AccessToken getAccessToken(String clientId, String clientSecret) throws IOException {
        authClient = new OkHttpClient.Builder()
                .addInterceptor(new ErrorHandlingInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getAPIBaseUrl())
                .addConverterFactory(getJacksonFactory())
                .client(authClient)
                .build();

        YelpFusionAuthApi client = retrofit.create(YelpFusionAuthApi.class);
        Call<AccessToken> call = client.getToken("client_credentials", appID, appSecret);
        accessToken = call.execute().body();
        return accessToken;
    }

    private static JacksonConverterFactory getJacksonFactory(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return JacksonConverterFactory.create(mapper);
    }

    public String getAPIBaseUrl() {
        return YELP_API_BASE_URL;
    }

}

