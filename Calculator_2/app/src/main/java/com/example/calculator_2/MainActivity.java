package com.example.calculator_2;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void getAns(View view){

        EditText in = (EditText) findViewById(R.id.input_number_1);
        String exp = in.getText().toString();
        exp = exp.replace(" ", "");
        exp = exp.replace("x", "*");
        // Didn't have time to implement polish notation+stack+decimal special case :P
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        try {
            String s = engine.eval(exp).toString();
            in.setText(s);
        } catch (ScriptException except) {
            in.setText("");
            in.setHint("Syntax Error!");
        }

    }

    // Digit buttons handler
    public void addDigit(View view){

        Button pressed_btn = (Button) findViewById(view.getId());
        EditText in = (EditText) findViewById(R.id.input_number_1);

        String digits = in.getText().toString() + pressed_btn.getText().toString();
        in.setText(digits);

    }

    public void undo(View view){

        EditText in = (EditText) findViewById(R.id.input_number_1);
        String value = in.getText().toString();

        // Nothing to remove
        if(value.length() < 1) return;

        in.setText(value.substring(0, value.length()-1));

    }

    public void clearInput(View view){
        EditText in = (EditText) findViewById(R.id.input_number_1);
        in.setText("");
    }

    public void dotify(View view){

        EditText in = (EditText) findViewById(R.id.input_number_1);
        validInputFill(in.getText().toString(), ".", in);

    }

    private void validInputFill(String s, String op, EditText txt){

        if(s.length() < 1) {
            // Beginning with a negative number
            if(op=="-") txt.setText(op);
            return;
        }
        switch(s.charAt(s.length()-1)) {

            case '.':
            case '-':
            case '+':
            case 'x':
            case '/':
                return;
            default:
                txt.setText(s + " " + op + " ");
                break;

        }

    }



    // Operations implementation
    public void add(View view){

        EditText in = (EditText) findViewById(R.id.input_number_1);
        validInputFill(in.getText().toString(), "+", in);

    }

    public void sub(View view){

        EditText in = (EditText) findViewById(R.id.input_number_1);
        validInputFill(in.getText().toString(), "-", in);

    }
    public void mul(View view){

        EditText in = (EditText) findViewById(R.id.input_number_1);
        validInputFill(in.getText().toString(), "x", in);

    }
    public void div(View view){

        EditText in = (EditText) findViewById(R.id.input_number_1);
        validInputFill(in.getText().toString(), "/", in);


    }
}