package com.vacuity.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vacuity.myapplication.R;
import com.vacuity.myapplication.models.model.NYTLYFhistory;
import com.vacuity.myapplication.models.model.nytlyfLab;

import java.util.List;

/**
 * Created by Gary Straub on 11/26/2017.
 */

public class historyFragment extends Fragment {
    private RecyclerView mNYTLYFRecyclerView;
    private historyAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.yelp_list_fragment, container, false);
        setHasOptionsMenu(false);
        mNYTLYFRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mNYTLYFRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();


        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //Toast.makeText(getActivity(), "Map: On User Visible", Toast.LENGTH_SHORT).show();
            //updateUI();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    private void updateUI() {
        nytlyfLab nLab = nytlyfLab.get(getActivity());
        List<NYTLYFhistory> hist = nLab.getHistory();

        if (mAdapter == null) {
            mAdapter = new historyAdapter(hist);

                mNYTLYFRecyclerView.setAdapter(mAdapter);


        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
    private class HistHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private NYTLYFhistory mHist;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        public HistHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.meetup_card_layout, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.Title);
            mDateTextView = (TextView) itemView.findViewById(R.id.meetup_description);
        }

        public void bind(NYTLYFhistory hist) {
            mHist = hist;
            mTitleTextView.setText(mHist.getmName());
            mDateTextView.setText(mHist.getmDate().toString());
            //mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
//            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
//            startActivity(intent);
        }
    }



    private class historyAdapter extends RecyclerView.Adapter<HistHolder> {

        private List<NYTLYFhistory> mHist;

        public historyAdapter(List<NYTLYFhistory> hist) {
            mHist = hist;
        }

        @Override
        public HistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new HistHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(HistHolder holder, int position) {
            NYTLYFhistory hist = mHist.get(position);
            holder.bind(hist);
        }

        @Override
        public int getItemCount() {
            return mHist.size();
        }
    }

}
