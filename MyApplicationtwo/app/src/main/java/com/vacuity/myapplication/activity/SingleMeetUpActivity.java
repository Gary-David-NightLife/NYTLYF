package com.vacuity.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.vacuity.myapplication.fragment.MeetUpFragment;

import java.util.UUID;

/**
 * Created by Gary Straub on 7/24/2017.
 */

public class SingleMeetUpActivity extends SingleFragmentActivity {

    private static final String SOME_STRING = "com.vacuity.storyfindertwo.story_id";
    private UUID meetupid;

    @Override
    protected Fragment createFragment() {
        //return new StoryFragment();
        meetupid = (UUID) getIntent().getSerializableExtra(SOME_STRING);
        return MeetUpFragment.newInstance(meetupid);
    }


    public static Intent newIntent(Context packageContext, UUID meetupid){
        Intent intent = new Intent(packageContext, SingleMeetUpActivity.class);
        intent.putExtra(SOME_STRING, meetupid);
        return intent;
    }

    //@Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment);
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        Fragment storyFragmentObj = fragmentManager.findFragmentById(R.id.fragment_container);
//        if(storyFragmentObj == null)
//            storyFragmentObj =  createFragment();
//
//        fragmentManager.beginTransaction().add(R.id.card_view, storyFragmentObj).commit();
        //MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        if(mapFragment == null) {
//            mapFragment = MapFragment.newInstance();
//            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
//        }
    }
//    public void getDirections(View view){
//        MeetUp aMeetUp = RecyclerViewAdapter.get(meetupid);
//        String longitude =  Double.toString(aMeetUp.getmLong());
//        String latitude = Double.toString(aMeetUp.getmLat());
//        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," +longitude);
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        startActivity(mapIntent);
//
//    }

//}
