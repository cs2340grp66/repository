package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.weichen.water_report.R;
import com.example.weichen.water_report.model.Quality_Report;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Submit_Quality_Reports_Activity extends AppCompatActivity {

    private ImageButton goBack;
    private EditText location;
    private EditText virus_PPM;
    private EditText contaminant_PPM;
    private Spinner conditions;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser userFirebase;
    private FirebaseUser user;


    private Quality_Report quality_report;

    private static String[] conditionSpinner = new String[]{"Safe", "Treatable", "Unsafe"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit__quality__reports_);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        userFirebase = FirebaseAuth.getInstance().getCurrentUser();

        goBack = (ImageButton) findViewById(R.id.goBack_submit_quality_reports);
        location = (EditText) findViewById(R.id.location_quality_reports);
        virus_PPM = (EditText) findViewById(R.id.virus_PPM_quality_report);
        contaminant_PPM = (EditText) findViewById(R.id.contaminant_PPM);

        conditions = (Spinner) findViewById(R.id.condition_spinner_quality_report);
        ArrayAdapter<String> conditionAdpt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, conditionSpinner);
        conditionAdpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditions.setAdapter(conditionAdpt);

    }


    /**
     * use to submit the quality report
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void submit_quality_report(View view) {
        final String _date, _location, _virus_PPM, _contaminant_PPM, _conditions;

        _location = location.getText().toString().trim();
        _virus_PPM = virus_PPM.getText().toString().trim();
        _conditions = (String) conditions.getSelectedItem();
        _contaminant_PPM = contaminant_PPM.getText().toString().trim();

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Calendar calobj = Calendar.getInstance();
        _date = (String)df.format(calobj.getTime());

        quality_report = new Quality_Report(_date, "", "", _location, _conditions, _virus_PPM, _contaminant_PPM);

        if (_conditions.length() > 0 && _virus_PPM.length() > 0 && _location.length() > 0 && _contaminant_PPM.length() > 0 ){

            databaseReference = databaseReference.child("user").child(userFirebase.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("userName").getValue(String.class);
                    quality_report.setRepoterName(name);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            databaseReference = databaseReference.getParent().getParent().child("quality_report");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String num = Long.toString(dataSnapshot.getChildrenCount() + 1);
                    quality_report.setReportNum(num);
                    databaseReference.child(num).setValue(quality_report);
                    Toast.makeText(Submit_Quality_Reports_Activity.this, "Submit Report Successfully", Toast.LENGTH_LONG).show();
                    goBack_submit_quality_reports(null);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            Toast.makeText(Submit_Quality_Reports_Activity.this, "Submit Report Fail! \nOne of input is empty. ", Toast.LENGTH_LONG).show();
        }


    }

    /**
     * go back to welcome activity
     */
    public void goBack_submit_quality_reports(View view) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String classes = dataSnapshot.child("classes").getValue(String.class);

                if (classes.equals("USER")){
                    startActivity(new Intent(Submit_Quality_Reports_Activity.this, WelcomActivity.class));
                } else if (classes.equals("WORKER")){
                    startActivity(new Intent(Submit_Quality_Reports_Activity.this, Worker_Welcome_Activity.class));
                } else if (classes.equals("MANAGER")){
                    startActivity(new Intent(Submit_Quality_Reports_Activity.this, Manager_Welcome_Activity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




}
