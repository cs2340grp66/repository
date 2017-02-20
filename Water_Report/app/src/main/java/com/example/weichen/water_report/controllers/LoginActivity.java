package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.weichen.water_report.R;
import com.example.weichen.water_report.model.User_Infor;

public class LoginActivity extends AppCompatActivity {


    private EditText passWord;
    private EditText userName;

    private Button _login;

    private ImageButton _goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.user_name_input);
        if (userName.getText().toString().length() == 0)
            userName.setError("User Name can't be empty!");
        passWord = (EditText) findViewById(R.id.password_input);
        if (passWord.getText().toString().length() == 0)
            passWord.setError("Password can't be empty!");

        _login = (Button) findViewById(R.id.login_button);
        _goBack = (ImageButton) findViewById(R.id.go_back_button);

        /*
            using to go back to initial screen from login
         */
        _goBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intents = new Intent(LoginActivity.this, InitialActivity.class);
                passWord = null;
                userName = null;
                startActivity(intents);
            }
        });
    }

    public void login_button(View v) {
        if (passWord == null || userName == null) {
            userName.setError("User Name can't be empty!");
            passWord.setError("Password can't be empty!");
        } else {
            if (userName.getText().toString().equals("user") && passWord.getText().toString().equals("pass")) {
                Intent intents = new Intent(LoginActivity.this, WelcomActivity.class);
                startActivity(intents);
            } else {
                passWord.setError("User or Password may not be correct!");
            }

        }

    }

}
