package com.example.tela2;

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

    public void processData(View v) {

        Intent it = getIntent();
        Bundle bundle = it.getExtras();

        // Retrieving user inputs from MainActivity
        double weight = bundle.getDouble("peso");
        int glasses = bundle.getInt("copos");
        String sex = bundle.getString("sexo");
        String fasting = bundle.getString("jejum");

        double alcohol = glasses*4.8;
        double coef = 0;

        if(fasting.equals("n")) coef = 1.1;
        else if(sex.equals("m")) coef = 0.7;
        else coef = 0.6;

        double bloodAlcohol = alcohol / (weight*coef);

        it = new Intent();
        it.putExtra("results", bloodAlcohol);

        setResult(RESULT_OK, it);
        finish();
    }
}