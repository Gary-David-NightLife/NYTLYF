package com.vacuity.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vacuity.myapplication.R;
import com.vacuity.myapplication.adapter.RecyclerViewAdapter;
import com.vacuity.myapplication.app.App;
import com.vacuity.myapplication.model.MeetUp;
import com.vacuity.myapplication.volley.VolleySingleton;

import java.util.UUID;

/**
 * Created by Gary Straub on 7/23/2017.
 */

public class MeetUpFragment extends Fragment{
    private MeetUp thisMeetUp;
    private TextView mTitle;
    private TextView Description;
    private NetworkImageView mImage;

    private static final String ARG_STORY_ID = "Card ID";

    public static MeetUpFragment newInstance(UUID storyID){
        Bundle args = new Bundle();
        args.putSerializable(ARG_STORY_ID, storyID);

        MeetUpFragment fragment = new MeetUpFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mStory = new Story();
//        UUID storyId = (UUID) getActivity().getIntent().getSerializableExtra(MainActivity.SOME_STRING);
        UUID  storyId = (UUID) getArguments().getSerializable(ARG_STORY_ID);
        thisMeetUp = RecyclerViewAdapter.get(storyId);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meetup, container, false);




        mTitle = (TextView) v.findViewById(R.id.frag_title);
        String s = Html.fromHtml(thisMeetUp.getmTitle()).toString();
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
        String d = Html.fromHtml(thisMeetUp.getmDescription()).toString();
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
        mImage = (NetworkImageView) v.findViewById(R.id.frag_img);
        ImageLoader imageLoader = VolleySingleton.getInstance(App.getContext()).getImageLoader();
        mImage.setImageUrl(thisMeetUp.getmImg(),imageLoader);
        return v;
    }



}
