package com.example.imc;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToReport(View v){

        // Getting necessary info from current View
        EditText name = (EditText) findViewById(R.id.nameBox);
        EditText age = (EditText) findViewById(R.id.ageBox);
        EditText weight = (EditText) findViewById(R.id.weightBox);
        EditText height = (EditText) findViewById(R.id.heightBox);

        TextView error = (TextView) findViewById(R.id.errorMsg);

        // Simple error handling.
        if(name.getText().toString().equals("")){
            error.setText("Insira um nome!");
            return;
        }
        if(age.getText().toString().equals("")){
            error.setText("Insira um valor de idade!");
            return;
        }
        if( weight.getText().toString().equals("") || Float.parseFloat(weight.getText().toString()) <= 0.0){
            error.setText("Insira um valor para peso!");
            return;
        }
        if(height.getText().toString().equals("") || Float.parseFloat(height.getText().toString()) <= 0.0){
            error.setText("Insira um valor para altura!");
            return;
        }

        Intent it = new Intent(getBaseContext(), Report.class);
        Bundle params = new Bundle();

        params.putString("name", name.getText().toString());
        params.putInt("age", Integer.parseInt(age.getText().toString()));
        params.putFloat("weight", Float.parseFloat(weight.getText().toString()) );
        params.putFloat("height", Float.parseFloat(height.getText().toString()) );


        it.putExtras(params);
        startActivity(it);

    }
}