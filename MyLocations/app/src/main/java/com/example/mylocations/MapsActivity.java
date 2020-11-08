package com.example.mylocations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Marker> markers;
    private double latitude;
    private double longitude;
    private double current_latitude;
    private double current_longitude;
    private String placeTxt;
    private final int LOCATION_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Would you believe there is such a place called Null Island?
        // It's precisely at lat 0.0, long 0.0
        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);
        placeTxt = getIntent().getStringExtra("title");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        navigateTo(latitude, longitude, placeTxt);

    }
    public void initCurrentLatLng(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        @SuppressLint("MissingPermission") android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        current_latitude = lastKnownLocation.getLatitude();
        current_longitude = lastKnownLocation.getLongitude();
    }

    public void navigateTo(double lat, double lng, String title){

        LatLng place = new LatLng(lat, lng);

        if(title == "Localização atual") {

            float results[] = new float[3];
            double currentCity_lat = Double.parseDouble(getString(R.string.currentCity_lat));
            double currentCity_lng = Double.parseDouble(getString(R.string.currentCity_lng));
            Location.distanceBetween(currentCity_lat, currentCity_lng, lat, lng, results);

            mMap.addMarker(new MarkerOptions().position(place).title(title)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            Toast toast = Toast.makeText(this,
                    "Você está a " + Float.toString(results[0]) + "metros de sua casa em viçosa.",
                    Toast.LENGTH_LONG);
            toast.show();
        } else {

            mMap.addMarker(new MarkerOptions()
                    .position(place)
                    .title(title));
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place,17));

    }

    public void navigateTo(View v){

        String place = v.getTag().toString();
        double lat = 0;
        double lng = 0;

        switch (place) {

            case "hometown":
                lat = Double.parseDouble(getString(R.string.hometown_lat));
                lng = Double.parseDouble(getString(R.string.hometown_lng));
                place = "Cidade natal - Cataguases";
                break;
            case "currentCity":
                lat = Double.parseDouble(getString(R.string.currentCity_lat));
                lng = Double.parseDouble(getString(R.string.currentCity_lng));
                place = "Cidade natal - Viçosa";
                break;
            case "department":
                lat = Double.parseDouble(getString(R.string.department_lat));
                lng = Double.parseDouble(getString(R.string.department_lng));
                place = "Departamento de Informática - UFV";
                break;
            default:
                Log.i("NAVIGATION", "Unknown tag provided");
                return;

        }

        navigateTo(lat, lng, place);

    }

    public void requestLocationPermission() {

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    LOCATION_PERMISSION);
        } else {
            initCurrentLatLng();
            navigateTo(current_latitude, current_longitude, "Localização atual");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    initCurrentLatLng();
                    navigateTo(current_latitude, current_longitude, "Localização atual");

                }
                return;
        }
    }

    public void getCurrentLocation(View v){
        requestLocationPermission();
    }
}

      