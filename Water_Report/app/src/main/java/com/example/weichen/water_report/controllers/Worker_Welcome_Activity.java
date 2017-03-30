package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.weichen.water_report.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Worker_Welcome_Activity extends AppCompatActivity {
    private Button logout;
    private Button edit_profile;
    private Button welcom_submit_report;
    private TextView userEmail;
    private Button welcom_submit_purity_report;

    private FirebaseAuth userAuth;

    private FirebaseUser user;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker__welcome_);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        userAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (userAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        logout = (Button) findViewById(R.id.logout);
        edit_profile = (Button) findViewById(R.id.profile_button);

        user = FirebaseAuth.getInstance().getCurrentUser();

        userEmail = (TextView) findViewById(R.id.user_email);

        databaseReference = databaseReference.child("user").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("userName").getValue(String.class);
                userEmail.setText("Hello: " + name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    /**
     * use to logout a use, will move to initial activity, and logout from database
     * @param view
     */
    public void logout(View view) {
        userAuth.signOut();
        startActivity(new Intent(Worker_Welcome_Activity.this, InitialActivity.class));

    }

    /**
     * move to profile activity
     * @param view view
     */
    public void edit_profile(View view) {
        startActivity(new Intent(Worker_Welcome_Activity.this, PersonalProfileActivity.class));
    }

    /**
     * move to the startActivity activity
     * @param view
     */
    public void welcom_submit_report (View view) {
        startActivity(new Intent(Worker_Welcome_Activity.this, SubmitReport.class));
    }

    /**
     * move to Submit_Quality_Reports_Activity
     * @param view
     */
    public void welcom_submit_purity_report (View view) {
        startActivity(new Intent(Worker_Welcome_Activity.this, Submit_Quality_Reports_Activity.class));
    }

    /**
     * move to the ViewReports activity
     * @param view
     */
    public void view_report(View view) {
        startActivity(new Intent(Worker_Welcome_Activity.this, ViewReports.class));
    }

    /**
     * move back to google map
     * @param view
     */
    public void map_button(View view) {
        startActivity(new Intent(Worker_Welcome_Activity.this, MapsActivity.class));
    }

}
