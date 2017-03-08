package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.icu.lang.UCharacterEnums;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.weichen.water_report.R;
import com.example.weichen.water_report.model.User_Infor;

import com.example.weichen.water_report.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SubmitReport extends AppCompatActivity {

    private EditText location;
    private EditText type;
    private EditText condition;
    private EditText virusPPM;
    private EditText contaminant;

    private ImageButton goBack_submission;

    private User_Infor user;

    private static String[] classes = new String[]{"USER", "WORKER","MANAGER","ADMINISTRATOR"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);

        location = (EditText) findViewById(R.id.sub_location);
        if (location.getText().toString().length() == 0)
            location.setError("Location can't be empty!");

        type = (EditText) findViewById(R.id.sub_type);
        if (type.getText().toString().length() < 1)
            type.setError("Needs to not be empty");

        condition = (EditText) findViewById(R.id.sub_condition);
        if (condition.getText().toString().length() < 1)
            condition.setError("Needs to not be empty");

        virusPPM = (EditText) findViewById(R.id.sub_virus);

        contaminant = (EditText) findViewById(R.id.sub_contaminant);


        goBack_submission = (ImageButton) findViewById(R.id.go_back_r_button_submission);

    }



    /**
     * Code for the back button on the registration
     *
     * @param view
     */
    public void goBack(View view) {
        Intent intents = new Intent(SubmitReport.this, InitialActivity.class);
        startActivity(intents);
    }
}