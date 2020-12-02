package com.example.bafometro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent it) {
        super.onActivityResult(requestCode, resultCode, it);

        Log.e("ACTIVITY_RESULT", "Processing request result");

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            double bloodAlcohol = it.getDoubleExtra("results", -1);
            Toast toast;
            String classification;

            Log.e("ACTIVITY_RESULT", Double.toString(bloodAlcohol) + " is the alcohol concentration");

            if(bloodAlcohol > 0.2) classification = "Pessoa Alcoolizada";
            else classification = "Pessoa NÃO Alcoolizada";

            toast = Toast.makeText(this, "Taxa de Alcoolemia:" +
                    " " + Double.toString(bloodAlcohol) +
                    " " + classification, Toast.LENGTH_LONG);
            toast.show();

        }


    }

    public void sendData(View v) {

        // Getting user data from txt Input fields
        EditText peso = (EditText) findViewById(R.id.pesoIn);
        EditText sexo = (EditText) findViewById(R.id.sexoIn);
        EditText copos = (EditText) findViewById(R.id.coposIn);
        EditText jejum = (EditText) findViewById(R.id.jejumIn);

        String p = peso.getText().toString();
        String s = sexo.getText().toString();
        String c = copos.getText().toString();
        String j = jejum.getText().toString();


        Bundle bundle = new Bundle();
        bundle.putDouble("peso", Double.parseDouble(p));
        bundle.putString("sexo", s);
        bundle.putInt("copos", Integer.parseInt(c));
        bundle.putString("jejum", j);


        Intent it = new Intent("SUPER_UNIQUE_INTENT_ID");
        it.putExtras(bundle);

        startActivityForResult(it, REQUEST_CODE);

    }

}