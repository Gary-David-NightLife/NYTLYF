package com.vacuity.myapplication.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.vacuity.myapplication.fragment.MapFragment;


/**
 * Created by Gary Straub on 7/24/2017.
 */

public class MapsActivity extends SingleFragmentActivity {

    private GoogleMap mMap;
    private static final int REQUEST_ERROR = 0;

    @Override
    protected Fragment createFragment() {
        return MapFragment.newInstance();
    }
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MapsActivity.class);
        return intent;
    }
    @Override
    protected void onResume() {
        super.onResume();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability.getErrorDialog(this,
                    errorCode,
                    REQUEST_ERROR,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            // Leave if services are unavailable.
                            finish();
                        }
                    });

            errorDialog.show();
        }
    }
}