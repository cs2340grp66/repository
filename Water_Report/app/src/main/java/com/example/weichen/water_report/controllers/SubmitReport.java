package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.weichen.water_report.R;
import com.example.weichen.water_report.model.Report_Infor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SubmitReport extends AppCompatActivity {

    private EditText location;
    private EditText type;
    private EditText condition;
    private String userName;
    private String reportNum;
    private String date;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser userFirebase;


    private ImageButton goBack_submission;
    private Button submitReport;

    private Report_Infor report;

    private static String[] classes = new String[]{"USER", "WORKER","MANAGER","ADMINISTRATOR"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(SubmitReport.this, InitialActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        userFirebase = FirebaseAuth.getInstance().getCurrentUser();



        location = (EditText) findViewById(R.id.sub_location);
        if (location.getText().toString().length() == 0)
            location.setError("Location can't be empty!");

        type = (EditText) findViewById(R.id.sub_type);
        if (type.getText().toString().length() < 1)
            type.setError("Water type can't be empty");

        condition = (EditText) findViewById(R.id.sub_condition);
        if (condition.getText().toString().length() < 1)
            condition.setError("Water condition can't be empty");

        submitReport = (Button) findViewById(R.id.comfirm_report);
        goBack_submission = (ImageButton) findViewById(R.id.go_back_r_button_submission);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void confirm_report(View view) {
        final String _location, _type, _condition, _date;

        _location = location.getText().toString().trim();
        _type = type.getText().toString().trim();
        _condition = condition.getText().toString().trim();

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Calendar calobj = Calendar.getInstance();
        _date = (String)df.format(calobj.getTime());

        report = new Report_Infor(_location, _type, _condition, _date, " ", " ");

        if (_condition.length() > 0 && _type.length() > 0 && _condition.length() > 0 ){

            databaseReference = databaseReference.child("user").child(userFirebase.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("userName").getValue(String.class);
                    report.setRepoterName(name);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            databaseReference = databaseReference.getParent().getParent().child("reports");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String num = Long.toString(dataSnapshot.getChildrenCount() + 1);
                    report.setReportNum(num);
                    databaseReference.child(num).setValue(report);
                    Toast.makeText(SubmitReport.this, "Submit Report Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SubmitReport.this, WelcomActivity.class));

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            Toast.makeText(SubmitReport.this, "Submit Report Fail! \nOne of input is empty. ", Toast.LENGTH_LONG).show();
        }


    }


    /**
     * Code for the back button on the registration
     *
     * @param view
     */
    public void go_back_r_button_submission(View view) {
        startActivity(new Intent(SubmitReport.this, WelcomActivity.class));
    }
}