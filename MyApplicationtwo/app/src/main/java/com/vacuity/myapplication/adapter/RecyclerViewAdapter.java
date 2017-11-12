package com.vacuity.myapplication.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vacuity.myapplication.R;
import com.vacuity.myapplication.app.App;
import com.vacuity.myapplication.connection.YelpAPITest;
import com.vacuity.myapplication.model.MeetUp;
import com.vacuity.myapplication.model.MeetUpLab;
import com.vacuity.myapplication.models.Business;
import com.vacuity.myapplication.volley.VolleySingleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gary Straub on 7/23/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private static List<MeetUp> myMeetUps;
    private static ArrayList<Business> mBusiness;
    private OnClickListener listener;
    private static MeetUpLab meetUpLab;
    public static YelpAPITest yelpAPITest;

    public static MeetUp get(UUID id){
        for(MeetUp tMU : myMeetUps){
            if(tMU.getmID().equals(id)){
                return tMU;
            }
        }
        return null;
    }
    public static void inject(){
        try {
            yelpAPITest = new YelpAPITest();
            yelpAPITest.execute();
            mBusiness = new ArrayList<>();
            mBusiness = yelpAPITest.getResults();

            //Log.e("Response", String.valueOf(mBusiness.get(0).getName()));

            //yelpAPITest.buissnessSearchTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        meetUpLab = new MeetUpLab();
//        mBusiness = meetUpLab.getBus();
        int max = myMeetUps.size();
        if(mBusiness!= null){
        if(mBusiness.size() < myMeetUps.size()){
            max = mBusiness.size();
        }
        for(int i = 0; i < max; i++){
                myMeetUps.get(i).setmTitle(mBusiness.get(i).getName());
                myMeetUps.get(i).setmDescription(mBusiness.get(i).getPhone());
                myMeetUps.get(i).setmImg(mBusiness.get(i).getImageUrl());
            }

        }
    }




    public interface OnClickListener {
        void onCardClick(MeetUp aMeetUp);
        void onPosterClick(MeetUp aMeetUp);
    }

    public RecyclerViewAdapter(List<MeetUp> meetupList) {



        Log.d("Bind View Holder", "this ran");
        this.myMeetUps = meetupList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meetup_card_layout, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MeetUp aMeetUp = myMeetUps.get(position);
        CardViewHolder cardViewHolder = (CardViewHolder) holder;
        cardViewHolder.setTitle(aMeetUp.getmTitle());
        cardViewHolder.setDescription(aMeetUp.getmDescription());
        cardViewHolder.setPosterUrl(aMeetUp.getmImg());
        if(listener!=null) {
            cardViewHolder.bindClickListener(listener, aMeetUp);
        }
    }
    public void updateDataSet(List<MeetUp> modelList) {
        inject();
        this.myMeetUps.clear();
        this.myMeetUps.addAll(modelList);
        notifyDataSetChanged();
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return myMeetUps.size();
    }
    private class CardViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView title;
        private TextView description;
        private NetworkImageView poster;

        /**
         * Class constructor.
         * @param view  layout of each item int the RecyclerView
         */
        CardViewHolder(View view) {
            super(view);
            this.cardView = (CardView) view.findViewById(R.id.card_view);
            this.title = (TextView) view.findViewById(R.id.Title);
            this.description = (TextView) view.findViewById(R.id.meetup_description);
            this.poster = (NetworkImageView) view.findViewById(R.id.nivPoster);

        }

        /**
         * append title text to Title:
         * @param title String of Title of movie
         */
        void setTitle(String title) {
            String t =  title;
            this.title.setText(t);
        }

        /**
         * append year text to Release Year:
         * @param myDescription String of year of release
         */
        void setDescription(String myDescription) {
            String y = myDescription ;
            String newIsh = Html.fromHtml(y).toString();
            this.description.setText(newIsh);
        }

        /**
         * Sends ImageRequest using volley using imageLoader and Cache.
         * This is pre-implemented feature of Volley to cache images for faster responses.
         * Check VolleySingleton class for more details.
         * @param imageUrl URL to poster of the Movie
         */
        void setPosterUrl(String imageUrl) {
            ImageLoader imageLoader = VolleySingleton.getInstance(App.getContext()).getImageLoader();
            this.poster.setImageUrl(imageUrl, imageLoader);
        }

        /**
         *
         * @param listener {@link OnClickListener}
         * @param aMeetUp
         */
        void bindClickListener(final OnClickListener listener, final MeetUp aMeetUp){
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCardClick(aMeetUp);
                }
            });

            poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onPosterClick(aMeetUp);
                }
            });
        }
    }
}
