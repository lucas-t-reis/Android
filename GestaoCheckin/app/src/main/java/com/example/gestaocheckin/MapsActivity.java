package com.example.gestaocheckin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng place;
    List<String> cat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        cat = new ArrayList<String>(Arrays.asList("Restaurante", "Bar", "Cinema", "Universidade", "Estádio", "Parque", "Outros"));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        addMarkers();
        if(place != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        }

    }
    public void addMarkers() {



        Cursor c = BD.getInstance().query("Checkin", new String[] {"Local", "cat", "qtdVisitas", "latitude", "longitude"}, "","");

        while(c.moveToNext()) {

            String name = c.getString(0);
            String category = cat.get(c.getInt(1));
            int visits = c.getInt(2);

            place = new LatLng(c.getDouble(3), c.getDouble(4));
            mMap.addMarker(new MarkerOptions().position(place).title(name).snippet("Categoria: " + category + " Visitas: " + visits));

        }
    }

    // Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_back:
                finish();
                Intent it1 = new Intent(this, MainActivity.class);
                startActivity(it1);
                break;
            case R.id.action_checkInManage:
                Intent it = new Intent(this, ManageActivity.class);
                startActivity(it);
                break;
            case R.id.action_mostVisited:
                Intent it2 = new Intent(this, ReportActivity.class);
                startActivity(it2);
                break;
            case R.id.action_normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.action_hibrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            default:
                break;
        }
        return true;
    }
}