package com.example.imc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent it = getIntent();
        Bundle params = it.getExtras();

        makeReport(params);

    }

    private void makeReport(Bundle info){

        TextView name = (TextView) findViewById(R.id.report_name_val);
        TextView age = (TextView) findViewById(R.id.report_age_val);
        TextView weight = (TextView) findViewById(R.id.report_weight_val);
        TextView height = (TextView) findViewById(R.id.report_height_val);
        TextView IMC = (TextView) findViewById(R.id.report_IMC_val);
        TextView classification = (TextView) findViewById(R.id.report_class_val);

        // Unpacking bundle
        name.setText(info.getString("name"));
        age.setText(""+info.getInt("age"));
        weight.setText(""+info.getFloat("weight"));
        height.setText(""+info.getFloat("height"));

        // Calculating BMI based on unpacked values
        float h = info.getFloat("height");
        float w = info.getFloat("weight");
        double value = (w/(Math.pow(h,2)));
        IMC.setText("" + Double.toString(value).substring(0,4));

        // Updating classification according to @param value
        classification.setText(getClassificationOf(value));
    }
    private String getClassificationOf(double value){

        if(value < 18.5) return "Abaixo do Peso";
        if(value <= 24.9) return "Saudável";
        if(value <= 29.9) return "Sobrepeso";
        if(value <= 34.9) return "Obesidade Grau I";
        if(value <= 39.9) return "Obesidade Grau II (severa)";

        return "Obesidade Grau III (mórbida)";

    }

    private void goBack(View v){
        finish();
    }




}
