package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.weichen.water_report.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton _goBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _goBack = (ImageButton) findViewById(R.id.go_back_from_register);

        /*
            using to go back to initial screen from register
         */
        _goBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intents = new Intent(RegisterActivity.this, InitialActivity.class);
                startActivity(intents);
            }
        });




    }
}
