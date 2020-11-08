package com.example.mylocations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void openMapOn(View v) {

        String tag = v.getTag().toString();
        Intent it = new Intent(this, MapsActivity.class);
        switch(tag) {
            case "hometown":
                Log.i("TAG_SWITCHING", "Going to " + tag);
                it.putExtra("title", "Cidade natal - Cataguases");
                it.putExtra("latitude", Double.parseDouble(getString(R.string.hometown_lat)));
                it.putExtra("longitude", Double.parseDouble(getString(R.string.hometown_lng)));
                break;
            case "currentCity":
                Log.i("TAG_SWITCHING", "Going to " + tag);
                it.putExtra("title", "Cidade atual - Viçosa");
                it.putExtra("latitude", Double.parseDouble(getString(R.string.currentCity_lat)));
                it.putExtra("longitude", Double.parseDouble(getString(R.string.currentCity_lng)));
                break;
            case "department":
                Log.i("TAG_SWITCHING", "Going to " + tag);
                it.putExtra("title", "Departamento de Informática - UFV");
                it.putExtra("latitude", Double.parseDouble(getString(R.string.department_lat)));
                it.putExtra("longitude", Double.parseDouble(getString(R.string.department_lng)));
                break;
            default:
                Log.i("TAG_SWITCHING", "Unknow tag case " + tag);
                break;
        }

        startActivity(it);


    }

    public void endActivity(View v) {
        this.finishAffinity();
    }
}