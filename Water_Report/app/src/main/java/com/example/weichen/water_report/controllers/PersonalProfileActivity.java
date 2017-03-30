package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class PersonalProfileActivity extends AppCompatActivity {

    private TextView _name;
    private TextView _email;
    private TextView _class;
    private TextView _phone;
    private TextView _address;

    private Button _edit;
    private ImageButton _goBack;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        _name = (TextView) findViewById(R.id.username_output);
        _email = (TextView) findViewById(R.id.userEmail_output);
        _class = (TextView) findViewById(R.id.userClass_output);
        _phone = (TextView) findViewById(R.id.userPhone_output);
        _address = (TextView) findViewById(R.id.userAdress_output);
        _edit = (Button) findViewById(R.id.edit_profile_button);
        _goBack = (ImageButton) findViewById(R.id.goBack_from_personal_profile);

        databaseReference = databaseReference.child("user").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                _name.setText( "Name:    " + dataSnapshot.child("userName").getValue(String.class));
                _class.setText("Class:   " + dataSnapshot.child("classes").getValue(String.class));
                _phone.setText("Phone Number:   " + dataSnapshot.child("phoneNum").getValue(String.class));
                _address.setText("Address:    " + dataSnapshot.child("address").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        _email.setText("Email:      "+user.getEmail());


    }

    /**
     * use to move to edit profile activity
     * @param view
     */
    public void edit_profile(View view) {
        startActivity(new Intent(PersonalProfileActivity.this, ProfileActivity.class));
    }

    /**
     * use to go back to welcome activity
     * @param view
     */
    public void goBack_from_personal_profile(View view) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String classes = dataSnapshot.child("classes").getValue(String.class);

                if (classes.equals("USER")){
                    startActivity(new Intent(PersonalProfileActivity.this, WelcomActivity.class));
                } else if (classes.equals("WORKER")){
                    startActivity(new Intent(PersonalProfileActivity.this, Worker_Welcome_Activity.class));
                } else if (classes.equals("MANAGER")){
                    startActivity(new Intent(PersonalProfileActivity.this, Manager_Welcome_Activity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
