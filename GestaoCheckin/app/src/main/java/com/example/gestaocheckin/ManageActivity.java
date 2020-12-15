package com.example.gestaocheckin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ManageActivity extends AppCompatActivity {

    List<String> places;
    ListView listView;
    ManageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);


        listView = findViewById(R.id.list_places);
        places = new ArrayList<String>();

        Cursor c = BD.getInstance().query("Checkin", new String[]{"Local"}, "", "");
        while(c.moveToNext()) {
            String place = c.getString(0);
            places.add(place);
        }

        adapter = new ManageAdapter(this, places);
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


    public void delete(View v) {

        String tag = v.getTag().toString();
        BD.getInstance().delete("Checkin", "Local='" + tag + "'");

        finish();
        startActivity(getIntent());

    }



}