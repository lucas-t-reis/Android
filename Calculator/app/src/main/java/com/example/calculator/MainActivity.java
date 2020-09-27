package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // Operations implementation
    public void add(View view){

        EditText value_1 = (EditText) findViewById(R.id.input_number_1);
        EditText value_2 = (EditText) findViewById(R.id.input_number_2);
        TextView result = (TextView) findViewById(R.id.result);

        double a = Double.parseDouble(value_1.getText().toString());
        double b = Double.parseDouble(value_2.getText().toString());
        a += b;

        String ans = (Double.toString(a));
        result.setText(ans);


    }

    public void sub(View view){

        EditText value_1 = (EditText) findViewById(R.id.input_number_1);
        EditText value_2 = (EditText) findViewById(R.id.input_number_2);
        TextView result = (TextView) findViewById(R.id.result);

        double a = Double.parseDouble(value_1.getText().toString());
        double b = Double.parseDouble(value_2.getText().toString());
        a -= b;

        String ans = (Double.toString(a));
        result.setText(ans);


    }
    public void mul(View view){

        EditText value_1 = (EditText) findViewById(R.id.input_number_1);
        EditText value_2 = (EditText) findViewById(R.id.input_number_2);
        TextView result = (TextView) findViewById(R.id.result);

        double a = Double.parseDouble(value_1.getText().toString());
        double b = Double.parseDouble(value_2.getText().toString());
        a *= b;

        String ans = (Double.toString(a));
        result.setText(ans);


    }
    public void div(View view){

        EditText value_1 = (EditText) findViewById(R.id.input_number_1);
        EditText value_2 = (EditText) findViewById(R.id.input_number_2);
        TextView result = (TextView) findViewById(R.id.result);

        double a = Double.parseDouble(value_1.getText().toString());
        double b = Double.parseDouble(value_2.getText().toString());
        a /= b;

        String ans = (Double.toString(a));
        result.setText(ans);


    }
}