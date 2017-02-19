package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.weichen.water_report.R;
import com.example.weichen.water_report.model.User_Infor;

public class WelcomActivity extends AppCompatActivity {

    Button logout;

    User_Infor _user;

//    String currentUserName = _user.getCurrentUse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        logout = (Button) findViewById(R.id.logout);
    }

    public void logout(View view) {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(WelcomActivity.this, InitialActivity.class);
                startActivity(intents);
            }
        });
    }
}
