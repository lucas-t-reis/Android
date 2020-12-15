package com.example.gestaocheckin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    List<String> places;
    ListView listView;
    PlacesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        listView = findViewById(R.id.list_places);
        places = new ArrayList<String>();

        Cursor c = BD.getInstance().query("Checkin", new String[]{"Local", "qtdVisitas"}, "", "");
        while(c.moveToNext()) {
            String place = c.getString(0);
            int visits = c.getInt(1);
            places.add(place + "_" + Integer.toString(visits));
        }

        adapter = new PlacesAdapter(this, places);
        listView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_back:
                finish();
                Intent it = new Intent(this, MainActivity.class);
                startActivity(it);
                break;
            default: break;
        }

        return true;
    }

}