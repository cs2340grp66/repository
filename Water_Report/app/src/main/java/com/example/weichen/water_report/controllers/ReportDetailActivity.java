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

public class ReportDetailActivity extends AppCompatActivity {


    private TextView report_num, reporter, report_time, water_type, location, condition;
    private ImageButton goBack;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        String passNum = null;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            passNum = extras.getString("index");
        }

        goBack = (ImageButton) findViewById(R.id.goBack_from_ReportDetailActivity) ;

        report_num = (TextView) findViewById(R.id.report_num);
        reporter = (TextView) findViewById(R.id.reporter);
        report_time = (TextView) findViewById(R.id.report_time);
        water_type = (TextView) findViewById(R.id.water_type);
        location = (TextView) findViewById(R.id.location);
        condition = (TextView) findViewById(R.id.water_condition);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("reports").child(passNum);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                report_num.setText("Report Number: " + dataSnapshot.child("reportNum").getValue(String.class));
                reporter.setText("Reporter Name: " + dataSnapshot.child("repoterName").getValue(String.class));
                report_time.setText("Report Date: " + dataSnapshot.child("date").getValue(String.class));
                water_type.setText("Water Type: " + dataSnapshot.child("type").getValue(String.class));
                location.setText("Water Location: " + dataSnapshot.child("location").getValue(String.class));
                condition.setText("Water Condition: " + dataSnapshot.child("condition").getValue(String.class));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    /**
     * use to goback to welcome activity
     * @param view
     */
    public void goBack_from_ReportDetailActivity(View view) {
        startActivity(new Intent(ReportDetailActivity.this, ViewReports.class));
    }
}
