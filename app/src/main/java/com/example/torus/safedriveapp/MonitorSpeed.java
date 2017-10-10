package com.example.torus.safedriveapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Random;

/**
 * Created by TORUS on 9/26/2017.
 */

public class MonitorSpeed extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    Handler handler;
    private Location mLocation;
    private GoogleApiClient mGoogleApiClient;
    PreferenceDetails preferenceDetails;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildGoogleApiClient();
        preferenceDetails=new PreferenceDetails(getApplicationContext());
        preferenceDetails.setCheck_safety(true);
        Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_LONG).show();
        if (mGoogleApiClient != null) mGoogleApiClient.connect();
        else
            buildGoogleApiClient();
        handler = new Handler(Looper.myLooper());
        handler.postDelayed(onEverySecond, 10000);


    }

   /* @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(),"Inside OnStart",Toast.LENGTH_LONG).show();
       handler = new Handler(Looper.myLooper());
        handler.postDelayed(onEverySecond,5000);
        return START_STICKY;

    }*/

    public void monitor_speed() {

        if (mLocation != null) {
            double speed=mLocation.getSpeed();
            if(speed>30)
            {
                preferenceDetails.setSpeed(speed+"");
            }
            Toast.makeText(getApplicationContext(), "Speed=" + mLocation.getSpeed(), Toast.LENGTH_LONG).show();
        } else {
            mGoogleApiClient.connect();
            Toast.makeText(getApplicationContext(), "Fetching...=", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mGoogleApiClient != null) {
            Toast.makeText(getApplicationContext(), "Location Connected", Toast.LENGTH_LONG).show();
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (mGoogleApiClient != null) mGoogleApiClient.connect();
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private Runnable onEverySecond = new Runnable() {
        @Override
        public void run() {

            monitor_speed();
            handler.postDelayed(onEverySecond, 10000);


        }

    };

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "Connection Failed="+connectionResult, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // flag=false;
        handler.removeCallbacks(onEverySecond);
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        preferenceDetails.setCheck_safety(false);
        Toast.makeText(getApplicationContext(), "Service Stoped,,", Toast.LENGTH_LONG).show();
    }
}
