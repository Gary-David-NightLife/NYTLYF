package com.vacuity.myapplication.fragment;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lyft.lyftbutton.LyftButton;
import com.lyft.lyftbutton.RideParams;
import com.lyft.lyftbutton.RideTypeEnum;
import com.lyft.networking.ApiConfig;
import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestButton;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.rides.client.ServerTokenSession;
import com.uber.sdk.rides.client.SessionConfiguration;
import com.vacuity.myapplication.R;
import com.vacuity.myapplication.app.App;
import com.vacuity.myapplication.connection.YelpFusionApi;
import com.vacuity.myapplication.connection.YelpFusionApiFactory;
import com.vacuity.myapplication.models.Business;
import com.vacuity.myapplication.models.model.YelpLab;
import com.vacuity.myapplication.models.model.nytlyfLab;
import com.vacuity.myapplication.volley.VolleySingleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Gary Straub on 11/13/2017.
 */

public class YelpFragment extends Fragment{
    private Business mBusiness;
    private TextView mTitle;
    private TextView Description;
    private TextView mRating;
    private TextView mCost;
    private TextView mCat;
    private NetworkImageView mImage;
    private NetworkImageView mImage1;
    private NetworkImageView mImage2;
    private NetworkImageView mImage3;

    private Button mButtonHere;
    private Button mButtonLike;
    private Button mButtonDislike;
    ImageSwitcher mImageswitch;
    ImageLoader imageLoader1;
    ImageLoader imageLoader2;
    ImageLoader imageLoader3;
    LayoutInflater mLayoutInflater;
    NetworkImageView m;
    private static final String PICKUP_NICK = "CurrentLocation";
    ArrayList<String> imgList;



    private static final String ARG_YELP_ID = "yelp id";

    public static YelpFragment newInstance(String yelpID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_YELP_ID, yelpID);

        YelpFragment fragment = new YelpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String yelpID = (String) getArguments().getSerializable(ARG_YELP_ID);
        mBusiness = YelpLab.get(getActivity()).getBusiness(yelpID);
    }
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.detailed_view, container, false);

        mTitle = (TextView) v.findViewById(R.id.frag_title);
        String s = Html.fromHtml(mBusiness.getName()).toString();
        mTitle.setText(s);
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Description = (TextView) v.findViewById(R.id.address);
        String d = Html.fromHtml(mBusiness.getLocation().getAddress1()).toString();
        Description.setText(d);
        Description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        String nString = mBusiness.getCategories().get(0).getTitle();
        for(int i = 1; i<mBusiness.getCategories().size(); i++){
            nString = nString + ", " + mBusiness.getCategories().get(i).getTitle();
        }

        mCat = (TextView) v.findViewById(R.id.categories);
        String h = (nString);
        mCat.setText(h);
        mCat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mRating = (TextView) v.findViewById(R.id.rating);
        String g = Double.toString(mBusiness.getRating());
        mRating.setText(g);
        mRating.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mCost = (TextView) v.findViewById(R.id.dollar_sign);
        String f = Html.fromHtml(mBusiness.getPrice()).toString();
        mCost.setText(f);
        mCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        mImage = (NetworkImageView) v.findViewById(R.id.frag_img);
        final ImageLoader imageLoader = VolleySingleton.getInstance(App.getContext()).getImageLoader();
        mImage.setImageUrl(mBusiness.getImageUrl(),imageLoader);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.setImageUrl(imgList.get(next()),imageLoader);
            }
        });



        try{
            SearchTask searchTask = new SearchTask();
            searchTask.execute(mBusiness.getId());
        }catch (IOException e){
            e.printStackTrace();
        }


        mButtonHere = (Button) v.findViewById(R.id.detail_here);
        mButtonHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Button Here", Toast.LENGTH_SHORT).show();
                nytlyfLab sHist = nytlyfLab.get(getActivity());
                sHist.add(mBusiness);


            }
        });


        mButtonDislike = (Button) v.findViewById(R.id.detail_dislike);
        mButtonDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Hello", "World");
                Toast.makeText(getContext(), "Button Dislike", Toast.LENGTH_SHORT).show();
            }
        });

        mButtonLike = (Button) v.findViewById(R.id.detail_like);
        mButtonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Button Like", Toast.LENGTH_SHORT).show();
            }
        });

        ApiConfig apiConfig = new ApiConfig.Builder()
                .setClientId("iXr4bNITC70T")
                .setClientToken("UoIFugJ+AS0VT9pNdEi/f8H1elJtNAO0iNm7z7BWJtQf2XVKcTpyQ2zvxGNrnoEvQzpnU9ksbcfTTt+O4G18q5n0E0fKglhaZu0fAxmyv/SSYllHdfQhfbk=")
                .build();

        GPSTracker cL = new GPSTracker(getContext());
        Location location = cL.getLocation();


        LyftButton lyftButton = (LyftButton) v.findViewById(R.id.lyft_button);
        lyftButton.setApiConfig(apiConfig);
        RideParams.Builder rideParamsBuilder = new RideParams.Builder()
                .setPickupLocation(location.getLatitude(), location.getLongitude())
                .setDropoffAddress(mBusiness.getLocation().getAddress1());
        rideParamsBuilder.setRideTypeEnum(RideTypeEnum.CLASSIC);
        lyftButton.setRideParams(rideParamsBuilder.build());
        lyftButton.load();

        SessionConfiguration config = new SessionConfiguration.Builder()
                // mandatory
                .setClientId("vEX5G1MidLTFVz9k6TES3Wai006DfI2U")
                // required for enhanced button features
                .setServerToken("YSYqR2H2OK_ZQXMnfbG4kHy0n39kF6wk-QeKrfHg")
                // required for implicit grant authentication
                .setRedirectUri("<REDIRECT_URI>")
                // required scope for Ride Request Widget features
                .setScopes(Arrays.asList(Scope.RIDE_WIDGETS))
                // optional: set sandbox as operating environment
                .setEnvironment(SessionConfiguration.Environment.SANDBOX)
                .build();
        UberSdk.initialize(config);
        ServerTokenSession session = new ServerTokenSession(config);

        YelpLab tLab = YelpLab.get(getContext());
        Business tmp = tLab.getBusiness().get(0);



        RideParameters rideParametersCheapestProduct = new RideParameters.Builder()
                .setPickupLocation(location.getLatitude(), location.getLongitude(), PICKUP_NICK, tmp.getLocation().getAddress1() )
                .setDropoffLocation(mBusiness.getCoordinates().getLatitude(), mBusiness.getCoordinates().getLongitude(), mBusiness.getName(),
                        mBusiness.getLocation().getAddress1())
                .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
                .build();


        RideRequestButton requestButton = (RideRequestButton) v.findViewById(R.id.uber_button);
        requestButton.setRideParameters(rideParametersCheapestProduct);
        requestButton.setSession(session);
        requestButton.loadRideInformation();



//        pager.addView(mImage2);

        return v;
    }
    private int curr= 0;

    private int next(){
        return (curr++)%imgList.size();
    }




    private class SearchTask extends AsyncTask<String, Integer, Business> {
        private ArrayList<Business> mBusiness;
        private Bitmap mBitmap;
        private Location mLocation;
        YelpFusionApiFactory yelpFusionApiFactory;
        YelpFusionApi yelpFusionApi;
        Business myResults;

        public SearchTask()throws IOException {
            yelpFusionApiFactory = new YelpFusionApiFactory();
        }

        @Override
        protected Business doInBackground(String... strings) {
            try{
                yelpFusionApi = yelpFusionApiFactory.createAPI("7qCtCchb7Otc0_bGSIkucQ",
                        "PFH4bBwEqBaiuRTkrVzqFvelfwI1gRqEHzxp5orCsl0ke0ORcN4lmOLpwPP9ept5");
                myResults = buissnessSearchTest(strings[0]);
            }catch(IOException e){
                e.printStackTrace();
            }
            return myResults;
        }

        private Business buissnessSearchTest(String key)throws IOException{
            Call<Business> call = yelpFusionApi.getBusiness(key);
            Business response = call.execute().body();
            assertNotNull(response);
            //Log.e("Response", String.valueOf(response.getBusinesses().size()));
//            for(int i =0; i<response.getBusinesses().size(); i++){
//                //Log.e("Response", String.valueOf(response.getBusinesses().get(i).getName()));
//            }
            return response;
        }

        @Override
        protected void onPostExecute(Business businesses) {

            imgList = new ArrayList<>();
            for(int i =0; i<businesses.getPhotos().size(); i++ ){
                imgList.add(businesses.getPhotos().get(i));
            }


            super.onPostExecute(businesses);
        }
    }

}
