package com.vacuity.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vacuity.myapplication.activity.SingleMeetUpActivity;
import com.vacuity.myapplication.adapter.RecyclerViewAdapter;
import com.vacuity.myapplication.controller.JsonController;
import com.vacuity.myapplication.model.MeetUp;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements
        SearchView.OnQueryTextListener,
        RecyclerViewAdapter.OnClickListener {

    private RecyclerViewAdapter adapter;
    JsonController controller;

    TextView textView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.tvEmptyRecyclerView);
        textView.setText("Search for movies using SearchView in toolbar");

//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(layoutManager);
//
//        adapter = new RecyclerViewAdapter(new ArrayList<MeetUp>());
//        adapter.setListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter(new ArrayList<MeetUp>());
        adapter.setListener(this);

        controller = new JsonController(
                new JsonController.OnResponseListener() {
                    @Override
                    public void onSuccess(List<MeetUp> myMeetups) {
                        if(myMeetups.size() > 0) {
                            textView.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.invalidate();
                            adapter.updateDataSet(myMeetups);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Failed to retrieve data");
                        Toast.makeText(MainActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                });
        controller.sendRequest("");



    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Search");
        searchView.setSubmitButtonEnabled(true);
        return true;
    }
    public boolean onQueryTextSubmit(String query) {
//        if(query.length() > 1) {
//            controller.cancelAllRequests();
//            controller.sendRequest("");
//            return false;
//        } else {
//            Toast.makeText(MainActivity.this, "Must provide more than one character", Toast.LENGTH_SHORT).show();
//            recyclerView.setVisibility(View.GONE);
//            textView.setVisibility(View.VISIBLE);
//            textView.setText("Must provide more than one character to search");
//            return true;
//        }
        controller.cancelAllRequests();
        controller.sendRequest(query);
        return false;


    }

    @Override
    public boolean onQueryTextChange(String newText) {
//        if(newText.length() > 1) {
//            controller.cancelAllRequests();
//            controller.sendRequest(newText);
//        } else if(newText.equals("")) {
//            recyclerView.setVisibility(View.GONE);
//            textView.setVisibility(View.VISIBLE);
//        }
        controller.cancelAllRequests();
        controller.sendRequest(newText);
        return true;

    }

    /**
     * Interface Implementation
     * <p>This method will be invoked when user press anywhere on cardview</p>
     */
    @Override
    public void onCardClick(MeetUp myMeetUp) {
        Intent intent = SingleMeetUpActivity.newIntent(getApplicationContext(),myMeetUp.getmID());
        startActivity(intent);

        Toast.makeText(this, myMeetUp.getmTitle() + " clicked", Toast.LENGTH_SHORT).show();
    }

    /**
     * Interface Implementation
     * <p>This method will be invoked when user press on poster of the movie</p>
     */
    @Override
    public void onPosterClick(MeetUp myMeetUp) {
        Intent intent = SingleMeetUpActivity.newIntent(getApplicationContext(),myMeetUp.getmID());
        startActivity(intent);

        Toast.makeText(this, myMeetUp.getmTitle() + " poster clicked", Toast.LENGTH_SHORT).show();
    }
    public void onClick(MeetUp myMeetUp){
        Intent intent = SingleMeetUpActivity.newIntent(getApplicationContext(),myMeetUp.getmID());
        startActivity(intent);
    }


}
