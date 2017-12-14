package com.vacuity.myapplication.fragment;

import android.location.Location;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vacuity.myapplication.R;
import com.vacuity.myapplication.app.App;
import com.vacuity.myapplication.models.Business;
import com.vacuity.myapplication.models.model.YelpLab;
import com.vacuity.myapplication.models.model.nytlyfLab;
import com.vacuity.myapplication.volley.VolleySingleton;

import com.lyft.lyftbutton.LyftButton;
import com.lyft.lyftbutton.RideParams;
import com.lyft.lyftbutton.RideTypeEnum;
import com.lyft.networking.ApiConfig;

/**
 * Created by Gary Straub on 11/13/2017.
 */

public class YelpFragment extends Fragment{
    private Business mBusiness;
    private com.vacuity.myapplication.models.Location mLocation;

    GPSTracker trak = new GPSTracker(getActivity());
    private Location mCurrentLocation = trak.getLocation();

    private TextView mTitle;
    private TextView Description;
    private NetworkImageView mImage;
    private Button mButtonHere;
    private Button mButtonLike;
    private Button mButtonDislike;



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
        mLocation = mBusiness.getLocation();
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
        Description = (TextView) v.findViewById(R.id.frag_desc);
        String d = Html.fromHtml(mBusiness.getDisplayPhone()).toString();
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
        Description = (TextView) v.findViewById(R.id.frag_addr);
        String z = mLocation.getAddress1().toString();
        Description.setText(z);
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
        mImage = (NetworkImageView) v.findViewById(R.id.frag_img);
        ImageLoader imageLoader = VolleySingleton.getInstance(App.getContext()).getImageLoader();
        mImage.setImageUrl(mBusiness.getImageUrl(),imageLoader);

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

        LyftButton lyftButton = (LyftButton) v.findViewById(R.id.lyft_button);
        lyftButton.setApiConfig(apiConfig);
        //double pickupLat = mCurrentLocation.getLatitude();
        //double pickupLon = mCurrentLocation.getLongitude();

        RideParams.Builder rideParamsBuilder = new RideParams.Builder()
                .setPickupLocation(37.7628,-122.497)//(double)mCurrentLocation.getLatitude(), (double)mCurrentLocation.getLongitude())
                .setDropoffAddress(mLocation.getAddress1()+" "+mLocation.getAddress2());
        rideParamsBuilder.setRideTypeEnum(RideTypeEnum.CLASSIC);

        lyftButton.setRideParams(rideParamsBuilder.build());
        lyftButton.load();



        return v;
    }

}
