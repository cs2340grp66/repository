package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.icu.lang.UCharacterEnums;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.weichen.water_report.R;
import com.example.weichen.water_report.model.User_Infor;

import com.example.weichen.water_report.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName;
    private EditText passWord;
    private EditText firstName;
    private EditText lastName;
    private EditText email;

    private Button create_Account_r_Button;

    private ImageButton goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = (EditText) findViewById(R.id.user_name_r_input);
        if (userName.getText().toString().length() == 0)
            userName.setError("User Name can't be empty!");

        passWord = (EditText) findViewById(R.id.password_r_input);
        if (passWord.getText().toString().length() == 0)
            passWord.setError("Password can't be empty!");

        firstName = (EditText) findViewById(R.id.first_name_r_input);
        if (firstName.getText().toString().length() == 0)
            firstName.setError("Password can't be empty!");

        lastName = (EditText) findViewById(R.id.last_name_r_input);
        if (lastName.getText().toString().length() == 0)
            lastName.setError("Password can't be empty!");

        email = (EditText) findViewById(R.id.email_r_input);
        if (email.getText().toString().length() == 0)
            email.setError("Password can't be empty!");

        create_Account_r_Button = (Button) findViewById(R.id.create_Account_r_Button);

        goBack = (ImageButton) findViewById(R.id.go_back_r_button);
    }

    /**
     * move to login screen
     * @param view
     */
    public void toLoginLayout(View view) {
        Intent intents = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intents);
    }

    /**
     * Code for the back button on the registration
     *
     * @param view
     */
    public void goBack(View view) {
        Intent intents = new Intent(RegisterActivity.this, InitialActivity.class);
        passWord = null;
        userName = null;
        startActivity(intents);
    }

    public void login_and_register() {
        Intent intents = new Intent(RegisterActivity.this, WelcomActivity.class);
        passWord = null;
        userName = null;
        startActivity(intents);
    }
 }

