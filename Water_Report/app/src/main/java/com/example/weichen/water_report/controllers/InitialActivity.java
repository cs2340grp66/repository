package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.weichen.water_report.R;

public class InitialActivity extends AppCompatActivity {

    Button _sign_in;
    Button _register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        _sign_in = (Button) findViewById(R.id.sign_in);
        _register = (Button) findViewById(R.id.register_button);
    }

    /**
     * move to login screen
     * @param view
     */
    public void toLoginLayout(View view) {
        _sign_in.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intents = new Intent(InitialActivity.this, LoginActivity.class);
                startActivity(intents);
            }
        });
    }

    /**
     * move to register screen
     * @param view
     */
    public void toRegisterLayout(View view) {
        _register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intents = new Intent(InitialActivity.this, R.class);
                startActivity(intents);
            }
        });
    }
}
