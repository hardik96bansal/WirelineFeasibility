package com.leptonmaps.wirelinefeasibility;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.leptonmaps.wirelinefeasibility.apputil.AppUtil;
import com.leptonmaps.wirelinefeasibility.apputil.SharePrefUtil;

public class SplashActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private final int WRITE_EXTERNAL_STORAGE = 1;
    private final int ACCESS_FINE_LOCATION = 2;
    private Dialog dialog = null;

    private GoogleApiClient googleApiClient = null;
    private static final int MY_LOCATION_PERMISSION = 1;
    private static final int RQS_GOOGLE_PLAY_SERVICES = 4;
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Typeface vodafoneRg =  AppUtil.getVodafoneRg(this);
        Typeface vodafoneRgBd =  AppUtil.getVodafoneRgBd(this);
        TextView mTvVersionName = (TextView)findViewById(R.id.tv_version_text);
        mTvVersionName.setTypeface(vodafoneRgBd);
        mTvVersionName.setText("Version "+AppUtil.getAppVersionString(getApplicationContext()) + " " + getString(R.string.beta));
        checkPlayServices();
        turnGPSOn();

    }


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                onCreateErrorDialog(getString(R.string.error),getString(R.string.playe_service_unavailable));
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case ACCESS_FINE_LOCATION:
                if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                    //TODO: turn on gps
                    turnGPSOn();
                }else{
                    onCreateErrorDialog(getString(R.string.error),getString(R.string.location_denied));
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED){
            if(resultCode == RESULT_OK) {
                gotoNextActivity();
                //  showToast("onActivityResult()YES");
            }else{
                onCreateErrorDialog(getString(R.string.error),getString(R.string.enable_location));
            }
        }else if(requestCode == RQS_GOOGLE_PLAY_SERVICES){
            if(resultCode == RESULT_OK) {
                int connectionResult = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
                if ( connectionResult == ConnectionResult.SUCCESS) {
                    turnGPSOn();
                }
                else {
                    Log.e(TAG, GooglePlayServicesUtil.getErrorString(connectionResult));
                    showToast(GooglePlayServicesUtil.getErrorString(connectionResult));
                    GooglePlayServicesUtil.getErrorDialog(connectionResult, this, RQS_GOOGLE_PLAY_SERVICES).show();
                }
            }else{
                onCreateErrorDialog(getString(R.string.error),getString(R.string.enable_location));
            }

        }else{
            onCreateErrorDialog(getString(R.string.error),getString(R.string.enable_location));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void gotoNextActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                if (SharePrefUtil.getToken(SplashActivity.this).length() == 0) {
                    intent = new Intent(SplashActivity.this,LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this,MapsActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    public void onCreateErrorDialog(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                SplashActivity.this.finish();
            }
        });
        builder.create();
        builder.show();
    }

    public void showToast(String msg) {
        Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void turnGPSOn() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(SplashActivity.this).addOnConnectionFailedListener(this).build();
            googleApiClient.connect();
        }else{
            if(googleApiClient.isConnected()){
                processGPSTurnOnProcess();
            }else{
                googleApiClient.connect();
            }
        }
    }


    private void processGPSTurnOnProcess(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        // **************************
        builder.setAlwaysShow(true); // this is the key ingredient
        // **************************

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // showToast("onResult():LocationSettingsStatusCodes.SUCCESS:");
                        // All location settings are satisfied. The client can
                        // initialize location
                        // requests here.
                        //TODO:
                        gotoNextActivity();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        //showToast("RESOLUTION_REQUIRED:");
                        // Location settings are not satisfied. But could be
                        // fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling
                            // startResolutionForResult(),
                            // and check the result in onActivityResult().

                            status.startResolutionForResult(SplashActivity.this, LocationSettingsStatusCodes.RESOLUTION_REQUIRED);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                            //showToast("RESOLUTION_REQUIRED:"+e);
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have
                        // no way to fix the
                        // settings so we won't show the dialog.
                        // showToast("onResult():LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:");
                        onCreateErrorDialog(getString(R.string.error),getString(R.string.unable_to_get_location));
                        break;
                }
            }
        });
    }


    //Callbacks

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //showToast("onConnected()");
        processGPSTurnOnProcess();
    }

    @Override
    public void onConnectionSuspended(int i) {
       /* if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_PERMISSION);
            return;
        }*/
        //showToast("onConnectionSuspended()");
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        int connectionStatus = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if ( connectionStatus == ConnectionResult.SUCCESS) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_PERMISSION);
                return;
            }
        }
        else {
            Log.e(TAG, GooglePlayServicesUtil.getErrorString(connectionStatus));
            GooglePlayServicesUtil.getErrorDialog(connectionStatus, this, RQS_GOOGLE_PLAY_SERVICES).show();
        }

       /* if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_PERMISSION);
            return;
        }*/


    }
}
