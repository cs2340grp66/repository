package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.weichen.water_report.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Quality_Report_details_Activity extends AppCompatActivity {

    private TextView reportNum, reporter, date, location, conditions, virus, contaminant;
    private ImageButton goBack;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality__report_details_);

        String passNum = null;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            passNum = extras.getString("indeX");
        }

        goBack = (ImageButton) findViewById(R.id.goBack_from_quality_report_detail);

        reportNum = (TextView) findViewById(R.id.qreport_reportNum);
        reporter = (TextView) findViewById(R.id.qreport_reporter);
        date = (TextView) findViewById(R.id.qreport_reportDate);
        location = (TextView) findViewById(R.id.qreport_location);
        conditions = (TextView) findViewById(R.id.qreport_condition);
        virus = (TextView) findViewById(R.id.qreport_virus);
        contaminant = (TextView) findViewById(R.id.qreport_contaminant);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("quality_report").child(passNum);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reportNum.setText("  Report Number: " + dataSnapshot.child("reportNum").getValue(String.class));
                reporter.setText("  Reporter Name: " + dataSnapshot.child("reporter").getValue(String.class));
                date.setText("  Report Date: " + dataSnapshot.child("date").getValue(String.class));
                virus.setText("  Virus PPM: " + dataSnapshot.child("virus_PPM").getValue(String.class));
                location.setText("  Water Location: " + dataSnapshot.child("locations").getValue(String.class));
                conditions.setText("  Water Condition: " + dataSnapshot.child("conditions").getValue(String.class));
                contaminant.setText("  Contaminant PPM: " + dataSnapshot.child("contaminant_PPM").getValue(String.class));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * using togo back to quality report list
     */
    public void goBack_from_quality_report_detail(View view) {
        startActivity(new Intent(Quality_Report_details_Activity.this, Quality_Report_LIst_Activity.class));
    }
}
