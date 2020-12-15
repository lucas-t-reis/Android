package com.example.gestaocheckin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LocationListener {

    // GPS stuff
    public LocationManager lm;
    public Criteria criteria;
    private final int LOCATION_PERMISSION = 1;
    public int REQUEST_TIME = 2500;
    public int MIN_DIST = 0;
    private LatLng CURRENT_POS;
    String provider;

    // View variables
    private TextView latView;
    private TextView lngView;
    private Spinner spinner;
    private AutoCompleteTextView autoComplete;

    // Utils
    private static BD database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        latView = findViewById(R.id.text_lat);
        lngView = findViewById(R.id.text_lng);


        // --------------- SPINNER ----------------- //
        database = BD.getInstance();
        spinner = (Spinner) findViewById(R.id.spinner_category);
        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        Cursor c = database.query("Categoria", new String[]{"nome"}, "", null);
        while (c.moveToNext()) {
            int id = c.getColumnIndex("nome");
            String category = "" + c.getString(id);
            categories.add(category);
        }
        c.close();

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        // --------------- LOCATION ----------------- //
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        PackageManager pm = getPackageManager();
        boolean hasGPS = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
        if (hasGPS) {
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            Log.i("LOCATION", "Using GPS");
        } else {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            Log.i("LOCATION", "Using Wi-fi or mobile data");
        }

        requestLocationPermission();
        autocomplete();
    }
    public void checkIn(View v) {

        AutoCompleteTextView input = (AutoCompleteTextView) findViewById(R.id.input_placeName);
        String name = input.getText().toString();
        int categoryId = (int) spinner.getSelectedItemId();

        if (name.matches("")) {
            Toast.makeText(this, "Por favor, digite um nome para o local.", Toast.LENGTH_SHORT).show();
            return;
        }


        Cursor c = BD.getInstance().query("Checkin",new String[] {"Local", "cat"},"Local='" + name + "'", "");
        if(c!=null) {

            // Tratar local ja existe mas categoria declarada diff
            ContentValues content = new ContentValues();
            content.put("Local", name);
            content.put("qtdVisitas", 1);
            content.put("cat", categoryId);

            if(CURRENT_POS!=null) {
                content.put("latitude", Double.toString(CURRENT_POS.latitude));
                content.put("longitude", Double.toString(CURRENT_POS.longitude));
            } else {
                String locationProvider = LocationManager.NETWORK_PROVIDER;
                @SuppressLint("MissingPermission") android.location.Location lastKnownLocation = lm.getLastKnownLocation(provider);
                CURRENT_POS = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            }

            if(c.getCount()==0) {
                Log.i("BD", "Adding new record to DB");
                database.insert("Checkin", content);
            } else {


                Cursor visits = database.query("Checkin", new String[]{"qtdVisitas"}, "Local='" + name + "'","");
                visits.moveToFirst();
                int n_visits = visits.getInt(0);
                content.put("qtdVisitas", n_visits+1);
                visits.close();

                int column = c.getColumnIndex("cat");
                c.moveToFirst();
                int currCat = c.getInt(column);
                if(currCat == categoryId) {
                    Log.i("BD","Updating place category.");
                    content.put("cat", categoryId);
                }

                database.update("Checkin", content, "Local='" + name + "'");

            }

        } else {
            Log.i("BD", "Query error");
        }

        finish();
        startActivity(getIntent());


    }
    public void autocomplete() {

        List<String> suggestions = new ArrayList<String>();
        autoComplete = (AutoCompleteTextView) findViewById(R.id.input_placeName);

        Cursor c = database.query("Checkin", new String[]{"Local"}, "", "");
        while(c.moveToNext()) {
            String s = c.getString(0);
            suggestions.add(s);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item,suggestions);

        autoComplete.setThreshold(2);
        autoComplete.setAdapter(adapter);

    }
    public void requestLocationPermission(){

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Permita o uso da localização para acessar seu local", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
        }
    }
    public void updateLocation() {
        latView.setText(Double.toString(CURRENT_POS.latitude));
        lngView.setText(Double.toString(CURRENT_POS.longitude));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch(requestCode){
            case LOCATION_PERMISSION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permissão Concedida", Toast.LENGTH_LONG).show();

                    provider = lm.getBestProvider(criteria, true);
                    @SuppressLint("MissingPermission") Location l = lm.getLastKnownLocation(provider);

                    CURRENT_POS = new LatLng(l.getLatitude(), l.getLongitude());
                    updateLocation();
                }
                else
                    Toast.makeText(this, "Permissão Negada: não é possível buscar a localização", Toast.LENGTH_LONG);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        provider = lm.getBestProvider(criteria, true);
        if (provider == null) { Log.i("LOCATION", "No provider found!"); }
        else {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)
                lm.requestLocationUpdates(provider, REQUEST_TIME, MIN_DIST, this);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        autocomplete();
    }
    @Override
    protected void onDestroy() {
        lm.removeUpdates(this);
        super.onDestroy();
    }
    @Override
    public void onLocationChanged(Location location){
        if(location != null){
            CURRENT_POS = new LatLng(location.getLatitude(), location.getLongitude());
            updateLocation();
        }
    }
    // Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent it = null;
        switch (item.getItemId()) {
            case R.id.action_checkInMap:
                it = new Intent(this, MapsActivity.class);
                break;
            case R.id.action_checkInManage:
                it = new Intent(this, ManageActivity.class);
                break;
            case R.id.action_mostVisited:
                it = new Intent(this, ReportActivity.class);
                break;
            default:
                Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                it = new Intent(this, MainActivity.class);
                break;
        }

        startActivity(it);

        return true;
    }
    // Spinner
    public void onItemSelected(AdapterView parent, View v, int posicao, long id) {
    }
    public void onNothingSelected(AdapterView arg0) {
    }


}