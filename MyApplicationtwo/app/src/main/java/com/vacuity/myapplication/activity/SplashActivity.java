package com.vacuity.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vacuity.myapplication.userinterface.TabActivity;

/**
 * Created by Gary Straub on 7/23/2017.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, TabActivity.class);

        startActivity(intent);
        finish();
    }
}
